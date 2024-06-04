package org.wsd.app.projection;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.wsd.app.domain.Product;
import org.wsd.app.events.ProductCreatedEvent;
import org.wsd.app.events.ProductUpdatedEvent;
import org.wsd.app.query.FindAllProducts;
import org.wsd.app.query.FindByProductIdQuery;
import org.eventa.core.streotype.EventHandler;
import org.eventa.core.streotype.ProjectionGroup;
import org.eventa.core.streotype.QueryHandler;
import org.wsd.app.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@ProjectionGroup
@RequiredArgsConstructor
public class ProductProjection {

    private final ProductRepository productRepository;

    @EventHandler(value = ProductCreatedEvent.class)
    public void on(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Product " + productCreatedEvent);
        Product product = new Product();
        product.setId(productCreatedEvent.getId());
        product.setProductName(productCreatedEvent.getProductName());
        product.setQuantity(productCreatedEvent.getQuantity());
        product.setPrice(productCreatedEvent.getPrice());

        Product persistedProduct = productRepository.save(product);
        log.info("Persisted Product : {}", persistedProduct);

        System.out.println("Thread Id : " + Thread.currentThread().getId()); // apply any logic here
    }

    @EventHandler(value = ProductUpdatedEvent.class)
    public void on(ProductUpdatedEvent productUpdatedEvent) {
        log.info("Product {}", productUpdatedEvent);

        Optional<Product> optionalProduct = productRepository.findById(productUpdatedEvent.getId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setProductName(productUpdatedEvent.getProductName());
            product.setQuantity(productUpdatedEvent.getQuantity());
            product.setPrice(productUpdatedEvent.getPrice());
            Product persistedProduct = productRepository.save(product);
            log.info("Updated Product : {}", persistedProduct);
        }

        System.out.println("Thread Id : " + Thread.currentThread().getId());
    }

    @QueryHandler
    public Product handle(FindByProductIdQuery findByProductIdQuery) {
        Optional<Product> optionalProduct = productRepository.findById(findByProductIdQuery.getProductId());
        return optionalProduct.orElse(null);
    }

    @QueryHandler
    public List<Product> handle(FindAllProducts products) {
        return productRepository.findAll();
    }

}
