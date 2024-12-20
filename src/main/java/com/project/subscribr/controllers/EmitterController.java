package com.project.subscribr.controllers;

import com.project.subscribr.managers.EmitterManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@CrossOrigin(origins = "*")
public class EmitterController {

    private final EmitterManger emitterManger;

    @Autowired
    public EmitterController() {
        this.emitterManger = EmitterManger.getInstance();
    }

    @GetMapping("/subscribe-to-events/{userId}")
    public SseEmitter subscribeToVideoNotifications(@PathVariable Long userId) {
        try {
            // Create emitter with no timeout. Timeouts are handled by frontend
            SseEmitter emitter = new SseEmitter(-1L);

            this.emitterManger.addEmitter(userId, emitter);
            System.out.println("New emitter added for user: " + userId);

            emitter.onCompletion(() -> this.emitterManger.removeEmitter(userId, emitter, "Emitter Complete"));
            emitter.onTimeout(() -> this.emitterManger.removeEmitter(userId, emitter, "Emitter Timeout"));

            return emitter;
        } catch (Exception e) {
            System.out.println("Received error during emitter subscription: " + e.getMessage());
            return null;
        }

    }
}
