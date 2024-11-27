package com.project.subscribr.orchestrators;

import com.project.subscribr.exceptions.VideoNotFoundException;
import com.project.subscribr.managers.EmitterManger;
import com.project.subscribr.models.entities.User;
import com.project.subscribr.models.entities.Video;
import com.project.subscribr.services.SubscriptionService;
import com.project.subscribr.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class VideoUploadedWebhookOrchestrator {
    private final SubscriptionService subscriptionService;
    private final VideoService videoService;
    private final EmitterManger emitterManger;

    @Autowired
    public VideoUploadedWebhookOrchestrator(SubscriptionService subscriptionService, VideoService videoService) {

        this.subscriptionService = subscriptionService;
        this.videoService = videoService;
        this.emitterManger = EmitterManger.getInstance();
    }

    public Video populateVideo(Long videoId) throws VideoNotFoundException {
        return this.videoService.getVideoById(videoId);
    }

    public void sendWebhookUpdates(Long userId, Video video) {
        // Get current emitterMap
        Map<Long, List<SseEmitter>> emitterMap = this.emitterManger.getEmitterMap();

        updateVideoUploader(emitterMap, userId, video);
        updateSubscribers(emitterMap, userId, video);
    }

    private void updateVideoUploader(Map<Long, List<SseEmitter>> emitterMap, Long userId, Video video) {
        List<SseEmitter> emitters = emitterMap.get(userId);
        String eventName = "video-upload-complete";
        sendUpdateToEmitters(userId, emitters, eventName, video);
    }

    private void updateSubscribers(Map<Long, List<SseEmitter>> emitterMap, Long userId, Video video) {
        List<Long> subscriberIds = this.subscriptionService.getSubscribersToUser(userId).stream()
                .map(User::getId)
                .toList();
        String eventName = "new-subscribed-video-uploaded";

        for (Long subscriberId : subscriberIds) {
            List<SseEmitter> emitters = emitterMap.get(subscriberId);
            sendUpdateToEmitters(subscriberId, emitters, eventName, video);
        }
    }

    private void sendUpdateToEmitters(Long userId, List<SseEmitter> emitters, String eventName, Object data) {
       if (emitters == null || emitters.isEmpty()) {
           return;
       }

        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
                System.out.println("Sent notification to emitter: " + data);
            } catch (IOException e) {
                // Remove dead emitter
                System.out.println("Unable to send data to emitter, adding to dead emitters.");
                deadEmitters.add(emitter);
            }
        }

        this.emitterManger.removeEmitters(userId, deadEmitters);
    }
}
