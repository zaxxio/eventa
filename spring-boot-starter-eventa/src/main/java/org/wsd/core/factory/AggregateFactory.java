package org.wsd.core.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.wsd.core.AggregateRoot;
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

    public <T extends AggregateRoot> T loadAggregate(UUID id, Class<T> aggregateClass) {
        List<BaseEvent> events = eventStore.getEventsFromAggregate(id);
        if (events.isEmpty()) {
            throw new IllegalStateException("No events found for aggregate with ID: " + id);
        }
        T aggregate = applicationContext.getBean(aggregateClass); // Creates an instance of the aggregate class
        aggregate.replayEvents(events);
        return aggregate;
    }
}
