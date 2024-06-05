package org.wsd.app.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eventa.core.commands.BaseCommand;

@Getter
@Setter
@SuperBuilder
public class UpdateProductCommand extends BaseCommand {
    private String productName;
    private Double quantity;
    private Double price;
}
