package org.wsd.core.eventstore;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.wsd.core.model.EventModel;

import java.util.List;
import java.util.UUID;

public interface EventStoreRepository extends MongoRepository<EventModel, UUID> {
    List<EventModel> findByAggregateIdentifier(UUID aggregateIdentifier);
}
