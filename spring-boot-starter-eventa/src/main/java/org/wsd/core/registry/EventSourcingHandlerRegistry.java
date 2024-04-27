package org.wsd.core.registry;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class EventHandlerRegistry {
    private final Map<Class<?>, List<Method>> eventSourcingHandler = new HashMap<>();

    public void registerHandler(Class<?> type, Method method) {
        eventSourcingHandler.computeIfAbsent(type, methods -> new LinkedList<>()).add(method);
    }

    public Method getHandler(Class<?> commandType) {
        return getMethod(commandType, eventSourcingHandler);
    }

    static Method getMethod(Class<?> commandType, Map<Class<?>, List<Method>> eventSourcingHandler) {
        List<Method> methods = eventSourcingHandler.get(commandType);
        if (methods == null && !methods.isEmpty()) {
            throw new RuntimeException("No EventSourcing Handler is registered");
        }
        if (methods.size() > 1) {
            throw new RuntimeException("More than one Event Sourcing handler is registered");
        }
        return methods.get(0);
    }
}
