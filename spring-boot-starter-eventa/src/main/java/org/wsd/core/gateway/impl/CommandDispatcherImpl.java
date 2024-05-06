package org.wsd.core.gateway.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.wsd.core.AggregateRoot;
import org.wsd.core.commands.BaseCommand;
import org.wsd.core.events.BaseEvent;
import org.wsd.core.eventstore.EventStore;
import org.wsd.core.factory.AggregateFactory;
import org.wsd.core.gateway.CommandDispatcher;
import org.wsd.core.interceptor.CommandInterceptor;
import org.wsd.core.registry.CommandHandlerRegistry;
import org.wsd.core.streotype.CommandHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

@Component
public class CommandDispatcherImpl implements CommandDispatcher, ApplicationContextAware {

    private final List<CommandInterceptor> interceptors;
    private final CommandHandlerRegistry commandHandlerRegistry;
    private ApplicationContext applicationContext;
    private final AggregateFactory aggregateFactory;
    private final EventStore eventStore;

    private CommandDispatcherImpl(List<CommandInterceptor> interceptors,
                                  CommandHandlerRegistry commandHandlerRegistry,
                                  AggregateFactory aggregateFactory,
                                  EventStore eventStore) {
        this.interceptors = interceptors;
        this.commandHandlerRegistry = commandHandlerRegistry;
        this.aggregateFactory = aggregateFactory;
        this.eventStore = eventStore;
    }

    @Override
    public <T extends BaseCommand> void send(T command) throws Exception {
        interceptors.forEach(commandInterceptor -> commandInterceptor.preHandle(command));
        Method commandHandlerMethod = commandHandlerRegistry.getHandler(command.getClass());
        if (commandHandlerMethod != null) {
            Class<?> aggregateClazz = commandHandlerMethod.getDeclaringClass();
            UUID aggregateId = command.getId(); // Assuming command contains a method to get the aggregate ID
            AggregateRoot aggregate = aggregateFactory.loadAggregate(aggregateId, aggregateClazz.asSubclass(AggregateRoot.class), commandHandlerMethod.getAnnotation(CommandHandler.class).constructor());
            try {
                commandHandlerMethod.invoke(aggregate, command);
                List<BaseEvent> uncommitedChanges = aggregate.getUncommitedChanges();
                eventStore.saveEvents(aggregateId, aggregateClazz.getSimpleName(), uncommitedChanges, aggregate.getVersion());
                aggregate.markChangesAsCommitted();
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        interceptors.forEach(commandInterceptor -> commandInterceptor.postHandle(command));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private boolean isConstructor(Method method) {
        return method.getDeclaringClass().getEnclosingConstructor() != null;
    }
}
