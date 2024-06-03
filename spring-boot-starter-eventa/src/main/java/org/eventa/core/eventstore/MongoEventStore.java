package org.eventa.core.eventstore;

import lombok.RequiredArgsConstructor;
import org.eventa.core.events.BaseEvent;
import org.eventa.core.producer.EventProducer;
import org.eventa.core.repository.EventStoreRepository;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class MongoEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;
    private final MongoTemplate mongoTemplate;

    @Override
    public void saveEvents(UUID aggregateId, String aggregateType, Iterable<BaseEvent> events, int expectedVersion, boolean constructor) throws Exception {
        List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId.toString());
        if (!isEmpty(eventStream) && expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            if (constructor) {
                throw new RuntimeException("Aggregate with Id " + aggregateId + " already exits");
            }
            throw new ConcurrencyFailureException("Concurrency problem with aggregate " + aggregateId);
        }
        int version = expectedVersion;
        for (BaseEvent event : events) {
            version++;
            event.setVersion(version);
            final EventModel eventModel = EventModel.builder()
                    .timestamp(new Date())
                    .aggregateIdentifier(String.valueOf(aggregateId))
                    .aggregateType(aggregateType)
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .baseEvent(event)
                    .build();
            final EventModel persistedEventModel = eventStoreRepository.save(eventModel);
            if (persistedEventModel.getId() != null) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    private static boolean isEmpty(List<EventModel> eventStream) {
        return eventStream.isEmpty();
    }

    @Override
    public List<BaseEvent> getEventsFromAggregate(UUID aggregateId) {
        List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId.toString());
        if (eventStream == null || isEmpty(eventStream)) {
            throw new RuntimeException("Aggregate " + aggregateId + " not found");
        }
        return eventStream.stream().map(EventModel::getBaseEvent).collect(Collectors.toList());
    }

    @Override
    public List<BaseEvent> findEventsAfterVersion(UUID aggregateId, int version) {
        Query query = new Query();
        query.addCriteria(Criteria.where("aggregateId").is(aggregateId).and("version").gte(version));
        return mongoTemplate.find(query, BaseEvent.class);
    }
}
