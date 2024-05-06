package org.wsd.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.wsd.core.eventstore.EventModel;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel, UUID> {
    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
