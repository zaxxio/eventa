package org.wsd.app.projection;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.wsd.app.events.ProductCreatedEvent;
import org.wsd.app.events.ProductUpdatedEvent;
import org.wsd.app.query.FindByProductIdQuery;
import org.eventa.core.streotype.EventHandler;
import org.eventa.core.streotype.ProjectionGroup;
import org.eventa.core.streotype.QueryHandler;

import java.util.List;

@Log4j2
@Service
@ProjectionGroup
public class ProductProjection {

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Product " + productCreatedEvent);
        System.out.println("Thread Id : "+Thread.currentThread().getId()); // apply any logic here
    }

    @EventHandler
    public void on(ProductUpdatedEvent productUpdatedEvent) {
        System.out.println("Product " + productUpdatedEvent);
        System.out.println("Thread Id : "+Thread.currentThread().getId());
    }

    @QueryHandler
    public List<Integer> handle(FindByProductIdQuery findByProductIdQuery) {
        System.out.println(findByProductIdQuery.getProductId());
        return List.of(1, 2, 3, 4, 5);
    }

}
