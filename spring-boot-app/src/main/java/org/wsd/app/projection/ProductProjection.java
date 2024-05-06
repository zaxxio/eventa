package org.wsd.app.projection;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.wsd.app.events.ProductCreatedEvent;
import org.wsd.app.events.ProductUpdatedEvent;
import org.wsd.core.streotype.EventHandler;
import org.wsd.core.streotype.ProjectionGroup;

@Log4j2
@Service
@ProjectionGroup
public class ProductProjection {

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Product " + productCreatedEvent);
    }

    @EventHandler
    public void on(ProductUpdatedEvent productUpdatedEvent) {
        System.out.println("Product " + productUpdatedEvent);
    }

}
