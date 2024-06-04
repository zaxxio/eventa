package org.wsd.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wsd.app.domain.Product;
import org.wsd.app.query.FindAllProducts;
import org.wsd.app.query.FindByProductIdQuery;
import org.wsd.app.query.ItemQuery;
import org.eventa.core.dispatcher.QueryDispatcher;
import org.eventa.core.query.ResponseType;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class QueryCommandHandler {

    private final QueryDispatcher queryDispatcher;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProductById(@PathVariable("productId") UUID productId) {
        final FindByProductIdQuery findByProductIdQuery = FindByProductIdQuery.builder()
                .productId(productId)
                .build();
        final Product result = queryDispatcher.dispatch(findByProductIdQuery, ResponseType.instanceOf(Product.class));
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProducts() {
        final FindAllProducts findAllProducts = FindAllProducts.builder().build();
        final List<Product> products = queryDispatcher.dispatch(findAllProducts, ResponseType.multipleInstancesOf(Product.class));
        return ResponseEntity.ok(products);
    }

}
