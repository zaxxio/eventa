package org.wsd.app.projection;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eventa.core.streotype.EventHandler;
import org.eventa.core.streotype.ProjectionGroup;
import org.eventa.core.streotype.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wsd.app.domain.Product;
import org.wsd.app.events.product.ProductCreatedEvent;
import org.wsd.app.events.product.ProductDeletedEvent;
import org.wsd.app.events.product.ProductReservedEvent;
import org.wsd.app.events.product.ProductUpdatedEvent;
import org.wsd.app.query.FindAllProducts;
import org.wsd.app.query.FindByProductIdQuery;
import org.wsd.app.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@ProjectionGroup
@RequiredArgsConstructor
public class ProductProjection {

    private final ProductRepository productRepository;

    @EventHandler(ProductCreatedEvent.class)
    @Transactional(transactionManager = "transactionManager")
    public void on(ProductCreatedEvent productCreatedEvent) {
        log.info("Product Created {}", productCreatedEvent);

        Product product = new Product();
        product.setId(productCreatedEvent.getId());
        product.setProductName(productCreatedEvent.getProductName());
        product.setQuantity(productCreatedEvent.getQuantity());
        product.setPrice(productCreatedEvent.getPrice());
        product.setThreadName(productCreatedEvent.getThreadName());
        Product persistedProduct = productRepository.save(product);

        log.info("Created Product : {}", persistedProduct);

        printThreadId();
    }


    @EventHandler(ProductReservedEvent.class)
    @Transactional(transactionManager = "transactionManager")
    public void on(ProductReservedEvent productReservedEvent) {

    }

    @EventHandler(ProductUpdatedEvent.class)
    @Transactional(transactionManager = "transactionManager")
    public void on(ProductUpdatedEvent productUpdatedEvent) {

        Optional<Product> optionalProduct = productRepository.findById(productUpdatedEvent.getId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setProductName(productUpdatedEvent.getProductName());
            product.setQuantity(productUpdatedEvent.getQuantity());
            product.setPrice(productUpdatedEvent.getPrice());
            product.setThreadName(productUpdatedEvent.getThreadName());
            Product persistedProduct = productRepository.save(product);
            log.info("Updated Product : {}", persistedProduct);
        }

        printThreadId();
    }


    @EventHandler(ProductDeletedEvent.class)
    @Transactional(transactionManager = "transactionManager")
    public void on(ProductDeletedEvent productDeletedEvent) {
        this.productRepository.deleteById(productDeletedEvent.getId());
        log.info("Deleted Product : {}", productDeletedEvent.getId());

        printThreadId();
    }

    private static void printThreadId() {
        log.debug("Thread Id : {}", Thread.currentThread().getId());
    }


    @QueryHandler
    @Transactional(transactionManager = "transactionManager")
    public Product handle(FindByProductIdQuery findByProductIdQuery) {
        Optional<Product> optionalProduct = productRepository.findById(findByProductIdQuery.getProductId());
        return optionalProduct.orElse(null);
    }

    @QueryHandler
    @Transactional(transactionManager = "transactionManager")
    public List<Product> handle(FindAllProducts products) {
        return productRepository.findAll();
    }

}
