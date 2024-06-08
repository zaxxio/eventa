package org.eventa.core.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.eventa.core.events.BaseEvent;
import org.eventa.core.dispatcher.EventDispatcher;

import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
@RequiredArgsConstructor
public class KafkaEventConsumer implements EventConsumer {

    private final EventDispatcher eventDispatcher;

    @Override
    @KafkaListener(topicPattern = "${eventa.kafka.event-store-name}", concurrency = "3", containerFactory = "kafkaListenerContainerFactory")
    public void consume(BaseEvent baseEvent, Acknowledgment ack) {
        log.debug("Received event: {}", baseEvent);
        log.debug("Thread Id : {}", Thread.currentThread().getId());
        CompletableFuture<Void> future = this.eventDispatcher.dispatch(baseEvent);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.debug("Successfully processed event: {}", baseEvent);
                ack.acknowledge();
            } else {
                log.error("Error processing event: {}", baseEvent, ex);
                // Handle exception (e.g., retry logic, dead-letter queue, etc.)
            }
        });
    }

    @DltHandler
    public void problem(BaseEvent baseEvent, Acknowledgment ack) {
        log.error("Not Processed {}", baseEvent);
    }

}

