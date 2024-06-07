package org.wsd.app.aggregates;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.eventa.core.streotype.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsd.app.commands.product.CreateProductCommand;
import org.wsd.app.commands.product.DeleteProductCommand;
import org.wsd.app.commands.product.UpdateProductCommand;
import org.wsd.app.events.product.ProductCreatedEvent;
import org.wsd.app.events.product.ProductDeletedEvent;
import org.wsd.app.events.product.ProductUpdatedEvent;
import org.eventa.core.aggregates.AggregateRoot;

import java.util.UUID;

@Data
@Aggregate
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AggregateSnapshot(interval = 2)
public class ProductAggregate extends AggregateRoot {

    private static final Logger log = LoggerFactory.getLogger(ProductAggregate.class);

    @RoutingKey
    private UUID id;
    private String productName;
    private double quantity;
    private double price;
    private String threadName;

    @CommandHandler(constructor = true)
    public void handle(CreateProductCommand createProductCommand) {

        if (createProductCommand.getProductName() == null) {
            throw new RuntimeException("Product name can not be null or empty.");
        }

        if (createProductCommand.getQuantity() <= 0) {
            throw new RuntimeException("Product quantity can not be less than or equal 0.");
        }

        apply(
                ProductCreatedEvent.builder()
                        .id(createProductCommand.getId())
                        .productName(createProductCommand.getProductName())
                        .threadName(Thread.currentThread().getName())
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
        this.threadName = productCreatedEvent.getThreadName();
    }

    @CommandHandler
    public void handle(UpdateProductCommand updateProductCommand) {

        if (updateProductCommand.getQuantity() <= 0) {
            throw new RuntimeException("Product quantity can not be less than or equal 0.");
        }

        apply(
                ProductUpdatedEvent.builder()
                        .id(updateProductCommand.getId())
                        .productName(updateProductCommand.getProductName())
                        .quantity(updateProductCommand.getQuantity())
                        .price(updateProductCommand.getPrice())
                        .threadName(Thread.currentThread().getName())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent productUpdatedEvent) {
        this.productName = productUpdatedEvent.getProductName();
        this.price = productUpdatedEvent.getPrice();
        this.quantity = productUpdatedEvent.getQuantity();
        this.threadName = productUpdatedEvent.getThreadName();
    }

    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand) {
        apply(
                ProductDeletedEvent.builder()
                        .id(deleteProductCommand.getId())
                        .productName(deleteProductCommand.getProductName())
                        .price(deleteProductCommand.getPrice())
                        .quantity(deleteProductCommand.getQuantity())
                        .threadName(Thread.currentThread().getName())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent productDeletedEvent) {
        this.productName = productDeletedEvent.getProductName();
        this.price = productDeletedEvent.getPrice();
        this.quantity = productDeletedEvent.getQuantity();
        this.threadName = productDeletedEvent.getThreadName();

    }

}
