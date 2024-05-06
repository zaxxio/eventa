package org.wsd.core.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.wsd.core.AggregateRoot;
import org.wsd.core.commands.BaseCommand;
import org.wsd.core.events.BaseEvent;
import org.wsd.core.eventstore.EventStore;

import java.util.List;
import java.util.UUID;

@Component
public class AggregateFactory {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private ApplicationContext applicationContext;

    public <T extends AggregateRoot> T loadAggregate(UUID id, Class<T> aggregateClass, boolean construct) throws Exception {
        if (construct) {
            return applicationContext.getBean(aggregateClass);
        }
        List<BaseEvent> events = eventStore.getEventsFromAggregate(id);
        if (events == null && events.isEmpty()) {
            throw new Exception("No event's found in the event store for this aggregate id " + id);
        }
        T aggregate = applicationContext.getBean(aggregateClass);
        aggregate.replayEvents(events);
        return aggregate;
    }

}
