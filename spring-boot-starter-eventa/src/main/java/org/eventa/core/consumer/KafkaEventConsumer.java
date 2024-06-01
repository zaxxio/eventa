package org.eventa.core.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.eventa.core.events.BaseEvent;
import org.eventa.core.dispatcher.EventDispatcher;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer implements EventConsumer {

    private final EventDispatcher eventDispatcher;

    @Override
    @KafkaListener(topicPattern = ".*Event$", concurrency = "2")
    public void consume(BaseEvent baseEvent, Acknowledgment ack) {
        this.eventDispatcher.dispatch(baseEvent);
        ack.acknowledge();
    }


}

