package org.wsd.core.registry;

import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class CommandHandlerRegistry {

    private final Map<Class<?>, List<Method>> routes = new HashMap<>();
    private final Map<Class<?>, List<Constructor>> routesAsConstructor = new HashMap<>();

    public void registerHandler(Class<?> type, Method method) {
        routes.computeIfAbsent(type, methods -> new LinkedList<>()).add(method);
    }

    public void registerHandler(Class<?> type, Constructor constructor) {
        routesAsConstructor.computeIfAbsent(type, constructors -> new ArrayList<>()).add(constructor);
    }

    public Constructor getHandlerAsConstructor(Class<?> commandType) {
        List<Constructor> constructors = routesAsConstructor.get(commandType);
        if (constructors == null && !constructors.isEmpty()) {
            throw new RuntimeException("No Command Handler is registered");
        }
        if (constructors.size() > 1) {
            throw new RuntimeException("More than one Command handler is registered");
        }
        return constructors.get(0);
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