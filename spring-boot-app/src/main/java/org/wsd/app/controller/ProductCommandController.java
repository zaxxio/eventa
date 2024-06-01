package org.wsd.app.controller;

import lombok.RequiredArgsConstructor;
import org.eventa.core.tag.ApiVersion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wsd.app.commands.CreateProductCommand;
import org.wsd.app.commands.UpdateProductCommand;
import org.wsd.app.model.ProductDTO;
import org.eventa.core.dispatcher.CommandDispatcher;

import java.util.UUID;

@RestController
@ApiVersion(value = "v1")
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<?> createProduct(ProductDTO productDTO) throws Exception {

        final CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .id(UUID.randomUUID())
                .productName(productDTO.getProductName())
                .quantity(productDTO.getQuantity())
                .price(productDTO.getPrice())
                .build();

        commandDispatcher.send(createProductCommand);
        return ResponseEntity.ok("");
    }


    @PutMapping
    public ResponseEntity<?> updateProduct(ProductDTO productDTO) throws Exception {

        final UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                .id(productDTO.getId())
                .productName(productDTO.getProductName())
                .quantity(productDTO.getQuantity())
                .price(productDTO.getPrice())
                .build();

        commandDispatcher.send(updateProductCommand);
        return ResponseEntity.ok("");
    }

}
