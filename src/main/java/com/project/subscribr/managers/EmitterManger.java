package com.project.subscribr.managers;

import com.project.subscribr.exceptions.UserInstanceAlreadyExists;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Singleton class
// Implemented using Bill Pugh Singleton Design
public class EmitterManger {
    private final Map<Long, SseEmitter> emitterMap;

    private EmitterManger() {
        // Initialize thread-safe map
        this.emitterMap = Collections.synchronizedMap(new HashMap<>());
    }

    private static class EmitterManagerHelper {
        private static final EmitterManger INSTANCE = new EmitterManger();
    }

    public static EmitterManger getInstance() {
        return EmitterManagerHelper.INSTANCE;
    }

    public void addEmitter(Long userId, SseEmitter emitter) throws UserInstanceAlreadyExists {
        if (emitterMap.containsKey(userId)) {
            throw new UserInstanceAlreadyExists();
        } else {
            emitterMap.put(userId, emitter);
        }
    }

    public void removeEmitter(Long userId) {
        emitterMap.remove(userId);
    }

    public Map<Long, SseEmitter> getEmitterMap() {
        synchronized (emitterMap) {
            return Collections.unmodifiableMap(emitterMap);
        }
    }


}
