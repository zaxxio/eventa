package org.wsd.app.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.wsd.core.events.BaseEvent;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class ProductCreatedEvent extends BaseEvent {
    private UUID id;
    private String productName;
    private Double quantity;
    private Double price;
}
