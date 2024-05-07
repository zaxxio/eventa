package org.wsd.app.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eventa.core.events.BaseEvent;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdatedEvent extends BaseEvent {
    private String productName;
    private Double quantity;
    private Double price;
}
