package org.wsd.core.producer;

import org.wsd.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent baseEvent) throws Exception;
}
