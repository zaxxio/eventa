package org.wsd.core.registry;

import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QueryHandlerRegistry {
    private final Map<Class<?>, List<Method>> routes = new HashMap<>();

    public void registerHandler(Class<?> type, Method method) {
        routes.computeIfAbsent(type, methods -> new LinkedList<>()).add(method);
    }

    public Method getHandler(Class<?> commandType) {
        List<Method> methods = routes.get(commandType);
        if (methods == null && !methods.isEmpty()) {
            throw new RuntimeException("No Command Handler is registered");
        }
        if (methods.size() > 1) {
            throw new RuntimeException("More than one Command handler is registered");
        }
        return methods.get(0);
    }
}