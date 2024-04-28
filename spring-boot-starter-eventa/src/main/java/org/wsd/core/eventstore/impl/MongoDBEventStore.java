package org.wsd.core.eventstore.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wsd.core.aggregate.AggregateRoot;
import org.wsd.core.eventstore.EventStore;
import org.wsd.core.eventstore.EventStoreRepository;
import org.wsd.core.model.EventModel;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
@Service
@RequiredArgsConstructor
public class MongoDBEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEvents(UUID aggregateId, Iterable<Object> events, int expectedVersion, AggregateRoot aggregateRoot) {
        final List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != -1) {
            throw new ConcurrencyFailureException("Concurrency problem with aggregate id : " + aggregateId);
        }
        int version = expectedVersion;
        for (Object event : events) {
            version++;
            final EventModel eventModel = EventModel.builder()
                    .timestamp(new Date())
                    .version(version)
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(aggregateRoot.getClass().getSimpleName())
                    .eventType(event.getClass().getTypeName())
                    .build();
            EventModel persistedEventModel = eventStoreRepository.save(eventModel);
            if (persistedEventModel.getId() != null) {
                final Message<Object> message = MessageBuilder
                        .withPayload(event)
                        .setHeader(KafkaHeaders.KEY, aggregateId.toString())
                        .setHeader("schema.version", "V2")
                        .setHeader(KafkaHeaders.TOPIC, event.getClass().getSimpleName())
                        .setHeader(KafkaHeaders.TIMESTAMP, System.currentTimeMillis())
                        .build();

                CompletableFuture<? extends SendResult<String, ?>> future = kafkaTemplate.send(message);
                future.thenAccept(uuidSendResult -> {
                    try {
                        log.info("Produced : " + future.get().getProducerRecord().value());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }).exceptionally(exception -> {
                    log.error(exception.getMessage());
                    return null;
                });
            }
        }
    }

    @Override
    public List<Object> getEvents(UUID aggregateId) {
        List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null && eventStream.isEmpty()) {
            throw new RuntimeException("Aggregate not found with " + aggregateId);
        }
        return new ArrayList<>(eventStream);
    }
}
