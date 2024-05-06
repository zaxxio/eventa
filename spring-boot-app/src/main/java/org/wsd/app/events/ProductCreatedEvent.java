package org.wsd.app.events;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.wsd.core.events.BaseEvent;


@ToString
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent extends BaseEvent {
    private String productName;
    private Double quantity;
    private Double price;
}
