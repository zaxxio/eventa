package org.wsd.core.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.wsd.core.events.BaseEvent;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer implements EventConsumer {
    @Override
    @KafkaListener(topicPattern = ".*Event$")
    public void consume(BaseEvent baseEvent, Acknowledgment acknowledgment) {
        System.out.println(baseEvent);
    }
}

