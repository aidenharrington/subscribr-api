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
    private final Long userId;
    private final Video video;
    private final EmitterManger emitterManger;

    @Autowired
    public VideoUploadedWebhookOrchestrator(UserService userService, Long userId, Long videoId) throws VideoNotFoundException {
        this.userService = userService;
        this.userId = userId;
        this.video = this.userService.getVideoById(videoId);
        this.emitterManger = EmitterManger.getInstance();
    }

    public void sendWebhookUpdates() {
        // Get current emitterMap
        Map<Long, SseEmitter> emitterMap = this.emitterManger.getEmitterMap();

        updateVideoUploader(emitterMap);
        updateSubscribers(emitterMap);
    }

    private void updateVideoUploader(Map<Long, SseEmitter> emitterMap) {
        SseEmitter videoUploaderEmitter = emitterMap.get(userId);
        String eventName = "video-upload-complete";

        sendUpdateToEmitter(videoUploaderEmitter, eventName, this.video);
    }

    private void updateSubscribers(Map<Long, SseEmitter> emitterMap) {
        List<Long> subscriberIds = this.userService.getSubscribersToUser(this.userId);;
        String eventName = "new-video-uploaded";

        for (Long subscriberId : subscriberIds) {
            SseEmitter emitter = emitterMap.get(userId);
            sendUpdateToEmitter(emitter, eventName, this.video);
        }

    }

    private void sendUpdateToEmitter(SseEmitter emitter, String eventName, Object data) {
        try {
            emitter.send(SseEmitter.event().name(eventName).data(data));
        } catch (IOException e) {
            // Remove dead emitter
            EmitterManger.getInstance().removeEmitter(userId);
        }
    }
}
