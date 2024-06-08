package org.wsd.app.saga;

import lombok.RequiredArgsConstructor;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.eventa.core.dispatcher.QueryDispatcher;
import org.eventa.core.streotype.EndSaga;
import org.eventa.core.streotype.Saga;
import org.eventa.core.streotype.SagaEventHandler;
import org.eventa.core.streotype.StartSaga;
import org.wsd.app.commands.product.ReserveProductCommand;
import org.wsd.app.events.order.OrderCreatedEvent;
import org.wsd.app.events.payment.PaymentProcessedEvent;
import org.wsd.app.events.product.ProductCreatedEvent;
import org.wsd.app.events.product.ProductReservedEvent;

@Saga
@RequiredArgsConstructor
public class OrderSaga {

    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(ProductCreatedEvent orderCreatedEvent) throws Exception {
        commandDispatcher.send(
                ReserveProductCommand.builder()
                        .id(orderCreatedEvent.getId())
                        .productName(orderCreatedEvent.getProductName())
                        .price(orderCreatedEvent.getPrice())
                        .threadName(orderCreatedEvent.getThreadName())
                        .quantity(orderCreatedEvent.getQuantity())
                        .build() // this will send ProductReserved Command
        );
        System.out.println("Saga Called");
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(ProductReservedEvent productReservedEvent) {
        System.out.println("Product Reserved!!");
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(PaymentProcessedEvent paymentProcessedEvent){

    }

}
