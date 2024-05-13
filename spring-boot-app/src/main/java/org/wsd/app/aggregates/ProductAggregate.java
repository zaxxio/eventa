package org.wsd.app.aggregates;

import lombok.NoArgsConstructor;
import org.eventa.core.streotype.*;
import org.wsd.app.commands.CreateProductCommand;
import org.wsd.app.commands.UpdateProductCommand;
import org.wsd.app.events.ProductCreatedEvent;
import org.wsd.app.events.ProductUpdatedEvent;
import org.eventa.core.aggregates.AggregateRoot;

import java.util.UUID;

@Aggregate
@NoArgsConstructor
@AggregateSnapshot(interval = 500)
public class ProductAggregate extends AggregateRoot {

    @RoutingKey
    private UUID id;
    private String productName;
    private Double quantity;
    private Double price;

    @CommandHandler(constructor = true)
    public void handle(CreateProductCommand createProductCommand) {
        apply(
                ProductCreatedEvent.builder()
                        .id(createProductCommand.getId())
                        .productName(createProductCommand.getProductName())
                        .quantity(createProductCommand.getQuantity())
                        .price(createProductCommand.getPrice())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        //super.id = productCreatedEvent.getId();
        this.productName = productCreatedEvent.getProductName();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @CommandHandler
    public void handle(UpdateProductCommand updateProductCommand) {
        apply(
                ProductUpdatedEvent.builder()
                        .id(updateProductCommand.getId())
                        .productName(updateProductCommand.getProductName())
                        .quantity(updateProductCommand.getQuantity())
                        .price(updateProductCommand.getPrice())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent productUpdatedEvent) {
        //super.id = productUpdatedEvent.getId();
        System.out.println(id);
        this.productName = productUpdatedEvent.getProductName();
        this.price = productUpdatedEvent.getPrice();
        this.quantity = productUpdatedEvent.getQuantity();
    }
}
