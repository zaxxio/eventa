package org.wsd.core.gateway;

import org.wsd.core.events.BaseEvent;

public interface EventDispatcher {
    void dispatch(BaseEvent baseEvent);
}
