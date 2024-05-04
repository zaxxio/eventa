package org.wsd.core.eventstore;

import org.springframework.stereotype.Service;
import org.wsd.core.events.BaseEvent;

import java.util.*;

@Service
public class InMemoryEventStore implements EventStore {

    private final Map<UUID, List<BaseEvent>> store = new HashMap<>();

    @Override
    public void save(UUID aggregateId, BaseEvent baseEvent, int expectedVersion) {
        store.computeIfAbsent(aggregateId, k -> new ArrayList<>()).add(baseEvent);
    }

    @Override
    public List<BaseEvent> getEventsFromAggregate(UUID aggregateId) {
        return store.getOrDefault(aggregateId, new ArrayList<>());
    }
}
