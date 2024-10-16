package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.VideoNotFoundException;
import com.project.subscribr.managers.EmitterManger;
import com.project.subscribr.models.entities.Video;
import com.project.subscribr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class VideoUploadedWebhookOrchestrator {
    private final UserService userService;
    private Long userId;
    private Video video;
    private final EmitterManger emitterManger;

    @Autowired
    public VideoUploadedWebhookOrchestrator(UserService userService) {
        this.userService = userService;
        this.emitterManger = EmitterManger.getInstance();
    }

    public void populateVideo(Long userId, Long videoId) throws VideoNotFoundException {
        this.userId = userId;
        this.video = this.userService.getVideoById(videoId);
    }

    public void sendWebhookUpdates() {
        // Get current emitterMap
        Map<Long, List<SseEmitter>> emitterMap = this.emitterManger.getEmitterMap();
        System.out.println("Emitter map: " + emitterMap);

        updateVideoUploader(emitterMap);
        updateSubscribers(emitterMap);
    }

    private void updateVideoUploader(Map<Long, List<SseEmitter>> emitterMap) {
        List<SseEmitter> emitters = emitterMap.get(userId);
        String eventName = "video-upload-complete";
        sendUpdateToEmitters(userId, emitters, eventName, this.video);
    }

    private void updateSubscribers(Map<Long, List<SseEmitter>> emitterMap) {
        List<Long> subscriberIds = this.userService.getSubscribersToUser(this.userId);;
        String eventName = "new-subscribed-video-uploaded";

        for (Long subscriberId : subscriberIds) {
            List<SseEmitter> emitters = emitterMap.get(subscriberId);
            sendUpdateToEmitters(subscriberId, emitters, eventName, this.video);
        }
    }

    private void sendUpdateToEmitters(Long userId, List<SseEmitter> emitters, String eventName, Object data) {
       if (emitters == null || emitters.isEmpty()) {
           System.out.println("No emitters found for userId: " + userId + "Emitter map: " + this.emitterManger.getEmitterMap());
           return;
       }

        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
                System.out.println("Sent notification to emitter: " + data);
            } catch (IOException e) {
                // Remove dead emitter
                deadEmitters.add(emitter);
                System.out.println("Removing dead emitter.");
            }
        }

        this.emitterManger.removeEmitters(userId, deadEmitters);
    }
}
