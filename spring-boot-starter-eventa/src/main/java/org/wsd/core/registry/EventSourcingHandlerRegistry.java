package org.wsd.core.registry;

import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Getter
@Lazy
@Component
public class EventSourcingHandlerRegistry {
    private final Map<Class<?>, List<Method>> eventSourcingHandler = new HashMap<>();

    public void registerHandler(Class<?> type, Method method) {
        eventSourcingHandler.computeIfAbsent(type, methods -> new ArrayList<>()).add(method);
    }

    public Method getHandler(Class<?> commandType) {
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
