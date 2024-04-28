package org.wsd.core.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.wsd.core.registry.CommandHandlerRegistry;
import org.wsd.core.streotype.Command;
import org.wsd.core.streotype.CommandHandler;

import java.util.Arrays;

@Log4j2
@Component
public class CommandHandlerProcessor implements BeanPostProcessor {

    @Lazy
    @Autowired
    private CommandHandlerRegistry commandHandlerRegistry;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(CommandHandler.class))
                .forEach(method -> {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    CommandHandler commandHandler = method.getAnnotation(CommandHandler.class);
                    if (parameterTypes.length > 1) {
                        log.error("More than one command type is used the CommandHandler");
                        throw new RuntimeException("More than one command type is used the CommandHandler");
                    }
                    Class<?> payload = commandHandler.payload();
                    Class<?> parameterType = parameterTypes[0];
                    if (!parameterType.isAssignableFrom(payload)) {
                        log.error("Payload type does not match with command handler type.");
                        throw new RuntimeException("Payload type does not match with command handler type.");
                    }

                    if (!payload.isAnnotationPresent(Command.class)) {
                        throw new RuntimeException("Command's should be annotated with @Command in " + payload);
                    }

                    if (!parameterType.isAnnotationPresent(Command.class)) {
                        throw new RuntimeException("Command's parameter should be annotated with @Command in " + method.getName());
                    }

                    commandHandlerRegistry.registerHandler(parameterType, method);
                });
        return bean;
    }
}
