package org.wsd.app.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.eventa.core.commands.BaseCommand;
import org.eventa.core.streotype.RoutingKey;

import java.util.UUID;

@Getter
@Setter
@ToString
@SuperBuilder
public class CreateProductCommand extends BaseCommand {
    @RoutingKey
    private UUID aggregateId;
    private String productName;
    private Double quantity;
    private Double price;
}
