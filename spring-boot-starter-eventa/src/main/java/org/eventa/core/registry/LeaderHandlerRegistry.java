package org.eventa.core.registry;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LeaderHandlerRegistry {

    private final ConcurrentHashMap<Class<?>, List<Method>> routes = new ConcurrentHashMap<>();

    public void registerHandler(Class<?> type, Method method) {
        routes.computeIfAbsent(type, methods -> new LinkedList<>()).add(method);
    }

    public Method getHandler(Class<?> commandType) {
        List<Method> methods = routes.get(commandType);
        if (methods == null || methods.isEmpty()) {
            throw new RuntimeException("No Command Handler is registered");
        }
        if (methods.size() > 1) {
            throw new RuntimeException("More than one Command handler is registered");
        }
        return methods.get(0);
    }

    public Set<Class<?>> getRegisteredClasses() {
        return routes.keySet();
    }

}
