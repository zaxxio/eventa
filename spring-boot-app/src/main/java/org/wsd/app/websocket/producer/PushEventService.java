package org.wsd.app.websocket.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wsd.app.push.PushMessageEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@Component
@RequiredArgsConstructor
public class PushEventService {

    private final KafkaTemplate<UUID, PushMessageEvent> kafkaTemplate;

    @Transactional(transactionManager = "kafkaTransactionManager", rollbackFor = Exception.class)
    public void pushEvent(PushMessageEvent pushMessageEvent) {
        final Message<?> message = MessageBuilder
                .withPayload(pushMessageEvent)
                .setHeader(KafkaHeaders.KEY, pushMessageEvent.getId())
                .setHeader("schema.version", "v1")
                .setHeader(KafkaHeaders.TOPIC, "PushEvent")
                .setHeader(KafkaHeaders.PARTITION, ThreadLocalRandom.current().nextInt(0, 2))
                .setHeader(KafkaHeaders.TIMESTAMP, System.currentTimeMillis())
                .build();
        CompletableFuture<? extends SendResult<UUID, ?>> future = kafkaTemplate.send(message);
        future.thenAccept(uuidSendResult -> {
            try {
                log.info("Produced : {}, Offset {}", future.get().getProducerRecord().value(), future.get().getRecordMetadata().offset());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(exception -> {
            log.error(exception.getMessage());
            return null;
        });
    }

}
