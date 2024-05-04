package org.wsd.app.aggregates;

import jakarta.annotation.PostConstruct;
import org.wsd.app.commands.CreateProductCommand;
import org.wsd.app.events.ProductCreatedEvent;
import org.wsd.core.AggregateRoot;
import org.wsd.core.streotype.Aggregate;
import org.wsd.core.streotype.CommandHandler;
import org.wsd.core.streotype.EventSourcingHandler;

@Aggregate
public class ProductAggregate extends AggregateRoot {

    @CommandHandler
    public void handle(CreateProductCommand createProductCommand) {
        apply(
                ProductCreatedEvent.builder()
                        .id(createProductCommand.getId())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        super.id = productCreatedEvent.getId();
    }
}
