package org.wsd.core.example.events;

import lombok.Builder;
import lombok.Data;
import org.wsd.core.streotype.EventSourced;

import java.util.UUID;

@Data
@Builder
@EventSourced
public class CashDepositedEvent {
    private UUID id;
    private String name;
    private Double balance;
    private int version;
}
