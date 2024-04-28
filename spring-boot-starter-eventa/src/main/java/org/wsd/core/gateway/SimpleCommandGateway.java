package org.wsd.core.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.wsd.core.registry.CommandHandlerRegistry;

import java.lang.reflect.Method;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Log4j2
@Component
@RequiredArgsConstructor
public class SimpleCommandGateway implements CommandGateway {

    private final CommandHandlerRegistry commandHandlerRegistry;
    private final ApplicationContext applicationContext;

    @Override
    public void send(Object command) {
        Method handler = commandHandlerRegistry.getHandler(command.getClass());
        if (handler != null) {
            Class<?> handlerClass = handler.getDeclaringClass();
            Object handlerInstance = applicationContext.getBean(handlerClass);
            try {
                handler.invoke(handlerInstance, command);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("Error invoking handler method: {}", e.getMessage(), e);
            } catch (Exception e) {
                log.error("Unexpected error: {}", e.getMessage(), e);
            }
        } else {
            log.warn("No handler found for command type: {}", command.getClass().getName());
        }
    }
}
