package org.wsd.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wsd.app.commands.CreateProductCommand;
import org.wsd.app.model.ProductDTO;
import org.wsd.core.gateway.CommandDispatcher;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<?> createProduct(ProductDTO productDTO) {

        final CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .id(productDTO.getId())
                .productName(productDTO.getProductName())
                .quantity(productDTO.getQuantity())
                .price(productDTO.getPrice())
                .build();

        commandDispatcher.send(createProductCommand);
        return ResponseEntity.ok("");
    }

}
