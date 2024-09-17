package com.project.subscribr.managers;

import com.project.subscribr.exceptions.UserInstanceAlreadyExists;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

// Singleton class
// Implemented using Bill Pugh Singleton Design
public class EmitterManger {
    private final Map<Long, List<SseEmitter>> emitterMap;

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

    public void addEmitter(Long userId, SseEmitter emitter)  {
        // If userId exists, add emitter to ArrayList.
        // If userId does NOT exist, create a new ArrayList and store emitter.
        emitterMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(emitter);
    }

    public void removeEmitter(Long userId, SseEmitter emitter) {
        List<SseEmitter> emitters = emitterMap.get(userId);

        if (emitters != null) {
            emitters.remove(emitter);

            if (emitters.isEmpty()) {
                emitterMap.remove(userId);
            }
        }
    }

    public void removeEmitters(Long userId, List<SseEmitter> emittersToRemove) {
        List<SseEmitter> emitters = this.emitterMap.get(userId);
        emitters.removeAll(emittersToRemove);
    }

    public Map<Long, List<SseEmitter>> getEmitterMap() {
        synchronized (emitterMap) {
            return Collections.unmodifiableMap(emitterMap);
        }
    }


}
