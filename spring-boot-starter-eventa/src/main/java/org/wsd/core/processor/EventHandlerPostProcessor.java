package org.wsd.core.processor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.wsd.core.registry.EventSourcingHandlerRegistry;
import org.wsd.core.streotype.EventSourced;
import org.wsd.core.streotype.EventSourcingHandler;

import java.util.Arrays;

@Log4j2
@Component

public class EventHandlerPostProcessor implements BeanPostProcessor {

    @Lazy
    @Autowired
    private EventSourcingHandlerRegistry eventSourcingHandlerRegistry;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(EventSourcingHandler.class))
                .forEach(method -> {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    EventSourcingHandler eventSourcingHandler = method.getAnnotation(EventSourcingHandler.class);

                    if (parameterTypes.length > 1) {
                        log.error("More than one event type is used in the EventSourcingHandler");
                        throw new RuntimeException("More than one event type is used the EventSourcingHandler");
                    }

                    Class<?> payload = eventSourcingHandler.payload();
                    Class<?> parameterType = parameterTypes[0];
                    if (!parameterType.isAssignableFrom(payload)) {
                        log.error("Payload type does not match with EventSourcingHandler method's parameter type.");
                        throw new RuntimeException("Payload type does not match with EventSourcingHandler method's parameter type.");
                    }

                    if (!payload.isAnnotationPresent(EventSourced.class)) {
                        throw new RuntimeException("Event's should be annotated with @Event.");
                    }

                    if (!parameterType.isAnnotationPresent(EventSourced.class)) {
                        throw new RuntimeException("Event's parameter should be annotated with @Event.");
                    }

                    eventSourcingHandlerRegistry.registerHandler(parameterType, method);
                });
        return bean;
    }
}
