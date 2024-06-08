package org.wsd.app.commands.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.eventa.core.commands.BaseCommand;

@Getter
@Setter
@ToString
@SuperBuilder
public class ReserveProductCommand extends BaseCommand {
    private String productName;
    private Double quantity;
    private Double price;
    private String threadName;
}
