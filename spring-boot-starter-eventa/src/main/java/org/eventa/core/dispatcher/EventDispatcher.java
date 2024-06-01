package org.eventa.core.dispatcher;

import org.eventa.core.events.BaseEvent;

public interface EventDispatcher {
    void dispatch(BaseEvent baseEvent);
}
