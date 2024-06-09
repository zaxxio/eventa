package org.wsd.app.saga;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.eventa.core.dispatcher.QueryDispatcher;
import org.eventa.core.streotype.EndSaga;
import org.eventa.core.streotype.Saga;
import org.eventa.core.streotype.SagaEventHandler;
import org.eventa.core.streotype.StartSaga;
import org.wsd.app.commands.product.ReserveProductCommand;
import org.wsd.app.events.payment.PaymentProcessedEvent;
import org.wsd.app.events.product.ProductCreatedEvent;
import org.wsd.app.events.product.ProductReservedCancelledEvent;
import org.wsd.app.events.product.ProductReservedEvent;

@Log4j2
@Saga
@RequiredArgsConstructor
public class ProductSaga {

    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {

        final ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .id(productCreatedEvent.getId())
                .productName(productCreatedEvent.getProductName())
                .price(productCreatedEvent.getPrice())
                .threadName(productCreatedEvent.getThreadName())
                .quantity(productCreatedEvent.getQuantity())
                .build();

        this.commandDispatcher.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                log.info("Problem {}", commandResultMessage.getException().getMessage());
            } else {
                log.info("Saga : {}", commandMessage.getCommand());
            }
        });
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void on(ProductReservedEvent productReservedEvent) throws Exception {
        log.info("Product Reserved Event : {}", productReservedEvent);
    }

    @SagaEventHandler(associationProperty = "id")
    public void on(ProductReservedCancelledEvent productReservedCancelledEvent) throws Exception {

    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(PaymentProcessedEvent paymentProcessedEvent) {

    }

}
