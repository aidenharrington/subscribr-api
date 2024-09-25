package com.project.subscribr.controllers;

import com.project.subscribr.managers.EmitterManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class EmitterController {

    private final EmitterManger emitterManger;

    @Autowired
    public EmitterController() {
        this.emitterManger = EmitterManger.getInstance();
    }

    @GetMapping("/subscribe/{userId}")
    public SseEmitter subscribeToVideoNotifications(@PathVariable Long userId) {
        SseEmitter emitter = new SseEmitter();

        this.emitterManger.addEmitter(userId, emitter);

        emitter.onCompletion(() -> this.emitterManger.removeEmitter(userId, emitter));
        emitter.onTimeout(() -> this.emitterManger.removeEmitter(userId, emitter));

        return emitter;
    }
}
