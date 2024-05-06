package org.wsd.app.projection;

import org.springframework.stereotype.Service;
import org.wsd.app.events.ProductCreatedEvent;
import org.wsd.core.streotype.EventHandler;
import org.wsd.core.streotype.ProjectionGroup;

@Service
@ProjectionGroup
public class ProductProjection {

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Product " + productCreatedEvent);
    }

}
