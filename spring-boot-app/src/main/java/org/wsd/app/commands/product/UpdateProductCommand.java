package org.wsd.app.commands.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eventa.core.commands.BaseCommand;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand extends BaseCommand {
    private String productName;
    private Double quantity;
    private Double price;
    private String threadName;
}
