package org.wsd.core.consumer;

import org.springframework.kafka.support.Acknowledgment;
import org.wsd.core.events.BaseEvent;

public interface EventConsumer {
    void consume(BaseEvent baseEvent, Acknowledgment acknowledgment);
}
