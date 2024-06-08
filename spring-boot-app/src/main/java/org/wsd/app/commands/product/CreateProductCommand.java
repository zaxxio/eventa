package org.wsd.app.commands.product;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.eventa.core.commands.BaseCommand;
import org.eventa.core.streotype.RoutingKey;

import java.util.UUID;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand extends BaseCommand {
    private String productName;
    private Double quantity;
    private Double price;
    private String threadName;
}
