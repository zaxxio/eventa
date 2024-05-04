package org.wsd.core.eventstore;

import org.wsd.core.events.BaseEvent;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void save(UUID aggregateId, BaseEvent baseEvent, int expectedVersion);
    List<BaseEvent> getEventsFromAggregate(UUID aggregateId);
}
