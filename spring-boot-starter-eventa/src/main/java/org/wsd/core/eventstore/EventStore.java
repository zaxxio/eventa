package org.wsd.core.eventstore;

import org.wsd.core.events.BaseEvent;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, String aggregateType, Iterable<BaseEvent> events, int expectedVersion) throws Exception;
    List<BaseEvent> getEventsFromAggregate(UUID aggregateId);
}
