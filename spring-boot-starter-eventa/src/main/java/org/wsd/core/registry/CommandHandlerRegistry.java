package org.wsd.core.registry;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Log4j2
@Lazy
@Component
public class CommandHandlerRegistry {

    private final Map<Class<?>, List<Method>> routes = new HashMap<>();

    public void registerHandler(Class<?> type, Method method) {
        routes.computeIfAbsent(type, methods -> new LinkedList<>()).add(method);
    }

    public Method getHandler(Class<?> commandType) {
        List<Method> methods = routes.get(commandType);
        if (methods == null && !methods.isEmpty()) {
            throw new RuntimeException("No EventSourcing Handler is registered");
        }
        if (methods.size() > 1) {
            throw new RuntimeException("More than one Event Sourcing handler is registered");
        }
        return methods.get(0);
    }

}
