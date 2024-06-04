package org.wsd.app.aggregates;

import lombok.NoArgsConstructor;
import org.eventa.core.streotype.*;
import org.wsd.app.commands.CreateProductCommand;
import org.wsd.app.commands.DeleteProductCommand;
import org.wsd.app.commands.UpdateProductCommand;
import org.wsd.app.events.ProductCreatedEvent;
import org.wsd.app.events.ProductDeletedEvent;
import org.wsd.app.events.ProductUpdatedEvent;
import org.eventa.core.aggregates.AggregateRoot;

import java.util.UUID;

@Aggregate
@NoArgsConstructor
@AggregateSnapshot(interval = 2)
public class ProductAggregate extends AggregateRoot {

    @RoutingKey
    private UUID id;
    private String productName;
    private double quantity;
    private double price;

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
        this.productName = productCreatedEvent.getProductName();
        this.price = productCreatedEvent.getPrice();
        this.quantity += 1;
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
        this.productName = productUpdatedEvent.getProductName();
        this.price = productUpdatedEvent.getPrice();
        this.quantity = productUpdatedEvent.getQuantity();
    }

    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand) {
        apply(
                ProductDeletedEvent.builder()
                        .id(deleteProductCommand.getId())
                        .productName(deleteProductCommand.getProductName())
                        .price(deleteProductCommand.getPrice())
                        .quantity(deleteProductCommand.getQuantity())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent productDeletedEvent) {
        this.productName = productDeletedEvent.getProductName();
        this.price = productDeletedEvent.getPrice();
        this.quantity = productDeletedEvent.getQuantity();
    }

}
