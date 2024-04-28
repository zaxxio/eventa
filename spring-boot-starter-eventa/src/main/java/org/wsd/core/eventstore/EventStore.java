package org.wsd.core.eventstore;

import org.wsd.core.aggregate.AggregateRoot;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, Iterable<Object> events, int expectedVersion, AggregateRoot aggregateRoot);

    List<Object> getEvents(UUID aggregateId);
}
