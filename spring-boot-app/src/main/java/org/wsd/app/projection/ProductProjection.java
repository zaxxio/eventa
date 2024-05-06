package org.wsd.app.projection;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.wsd.app.events.ProductCreatedEvent;
import org.wsd.app.events.ProductUpdatedEvent;
import org.wsd.app.model.ProductDTO;
import org.wsd.app.query.FindByProductIdQuery;
import org.wsd.core.streotype.EventHandler;
import org.wsd.core.streotype.ProjectionGroup;
import org.wsd.core.streotype.QueryHandler;
import scala.Int;

import java.util.List;

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

    @QueryHandler
    public List<Integer> handle(FindByProductIdQuery findByProductIdQuery) {
        return List.of(1, 2, 3, 4, 5);
    }

}
