package org.wsd.app.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.wsd.core.events.BaseEvent;

@Getter
@Setter
@SuperBuilder
public class ProductCreatedEvent extends BaseEvent {
}
