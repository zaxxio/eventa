package org.wsd.core.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.wsd.core.registry.CommandHandlerRegistry;
import org.wsd.core.registry.EventSourcingHandlerRegistry;
import org.wsd.core.streotype.Aggregate;
import org.wsd.core.streotype.CommandHandler;
import org.wsd.core.streotype.EventSourcingHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class PostProcessor implements ApplicationListener<ContextRefreshedEvent> {
    private final ApplicationContext applicationContext;
    private final CommandHandlerRegistry commandHandlerRegistry;
    private final EventSourcingHandlerRegistry eventSourcingHandlerRegistry;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final Map<String, Object> aggregates = this.applicationContext.getBeansWithAnnotation(Aggregate.class);
        for (Map.Entry<String, Object> entry : aggregates.entrySet()) {
            Class<?> aClass = entry.getValue().getClass();

            // Capture Constructor for Aggregate instance
            Arrays.stream(aClass.getConstructors())
                    .filter(constructor -> constructor.isAnnotationPresent(CommandHandler.class))
                    .forEach(constructor -> {
                        Class<?>[] parameterTypes = constructor.getParameterTypes();
                        if (parameterTypes.length == 1) {
                            commandHandlerRegistry.registerHandler(parameterTypes[0], constructor);
                        } else {
                            log.error("Problem");
                        }
                    });

            Arrays.stream(aClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(CommandHandler.class))
                    .forEach(method -> {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length == 1) {
                            commandHandlerRegistry.registerHandler(parameterTypes[0], method);
                        } else {
                            log.error("Problem");
                        }
                    });


            Arrays.stream(aClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(EventSourcingHandler.class))
                    .forEach(method -> {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length == 1) {
                            eventSourcingHandlerRegistry.registerHandler(parameterTypes[0], method);
                        } else {
                            log.error("Problem in");
                        }
                    });
        }
    }
}
