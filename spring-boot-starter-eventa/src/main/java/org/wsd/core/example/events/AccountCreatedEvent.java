package org.wsd.core.example.events;

import lombok.Builder;
import lombok.Data;
import org.wsd.core.streotype.EventSourced;

import java.util.UUID;

@Data
@Builder
@EventSourced
public class AccountCreatedEvent {
    private UUID id;
    private String name;
    private Double balance;
}
