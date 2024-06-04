package org.eventa.core.dispatcher.impl;

import org.eventa.core.eventstore.EventStore;
import org.eventa.core.factory.AggregateFactory;
import org.eventa.core.interceptor.CommandInterceptorRegisterer;
import org.eventa.core.repository.SnapshotRepository;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Component;
import org.eventa.core.aggregates.AggregateRoot;
import org.eventa.core.commands.BaseCommand;
import org.eventa.core.events.BaseEvent;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.eventa.core.registry.CommandHandlerRegistry;
import org.eventa.core.streotype.CommandHandler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

@Component
public class CommandDispatcherImpl implements CommandDispatcher {

    private final CommandInterceptorRegisterer commandInterceptorRegisterer;
    private final CommandHandlerRegistry commandHandlerRegistry;
    private final AggregateFactory aggregateFactory;
    private final EventStore eventStore;
    private final SnapshotRepository snapshotRepository;

    public CommandDispatcherImpl(CommandInterceptorRegisterer commandInterceptorRegisterer,
                                 CommandHandlerRegistry commandHandlerRegistry,
                                 AggregateFactory aggregateFactory,
                                 EventStore eventStore,
                                 SnapshotRepository snapshotRepository) {
        this.commandInterceptorRegisterer = commandInterceptorRegisterer;
        this.commandHandlerRegistry = commandHandlerRegistry;
        this.aggregateFactory = aggregateFactory;
        this.eventStore = eventStore;
        this.snapshotRepository = snapshotRepository;
    }

    @Override
    public <T extends BaseCommand> void send(T command) throws Exception {
        commandInterceptorRegisterer.getCommandInterceptors().forEach(commandInterceptor -> commandInterceptor.preHandle(command));
        Method commandHandlerMethod = commandHandlerRegistry.getHandler(command.getClass());
        if (commandHandlerMethod != null) {
            Class<?> aggregateClass = commandHandlerMethod.getDeclaringClass();
            UUID aggregateId = command.getId();
            AggregateRoot aggregate = aggregateFactory.loadAggregate(aggregateId, aggregateClass.asSubclass(AggregateRoot.class), commandHandlerMethod.getAnnotation(CommandHandler.class).constructor());
            commandHandlerMethod.invoke(aggregate, command);
            List<BaseEvent> uncommittedChanges = aggregate.getUncommittedChanges();
            try {
                eventStore.saveEvents(aggregateId, aggregateClass.getSimpleName(), uncommittedChanges, aggregate.getVersion(), commandHandlerMethod.getAnnotation(CommandHandler.class).constructor());
                aggregate.markChangesAsCommitted();
                /*if (aggregate.getVersion() % aggregate.getSnapshotInterval() == 0) {
                    final Snapshot snapshot = aggregate.takeSnapshot();
                    this.snapshotRepository.save(snapshot);
                }*/
            } catch (ConcurrencyFailureException e) {
                // Log and handle the concurrency issue appropriately
                throw e;
            }
        }
        commandInterceptorRegisterer.getCommandInterceptors().forEach(commandInterceptor -> commandInterceptor.postHandle(command));
    }
}