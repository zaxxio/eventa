package org.eventa.core.eventstore;

import org.eventa.core.events.BaseEvent;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, String aggregateType, Iterable<BaseEvent> events, int expectedVersion, boolean constructor) throws Exception;
    List<BaseEvent> getEventsFromAggregate(UUID aggregateId);
    List<BaseEvent> findEventsAfterVersion(UUID aggregateId, int version);
}
