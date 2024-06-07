package org.wsd.app.controller;

import lombok.RequiredArgsConstructor;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.eventa.core.tag.ApiVersion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wsd.app.commands.product.CreateProductCommand;
import org.wsd.app.commands.product.DeleteProductCommand;
import org.wsd.app.commands.product.UpdateProductCommand;
import org.wsd.app.model.ProductDTO;

import java.util.List;
import java.util.UUID;

@RestController
@ApiVersion(value = "v1")
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody List<ProductDTO> productDTOS) throws Exception {

        for (ProductDTO productDTO : productDTOS) {
            final CreateProductCommand createProductCommand = CreateProductCommand.builder()
                    .id(UUID.randomUUID())
                    .productName(productDTO.getProductName())
                    .quantity(productDTO.getQuantity())
                    .price(productDTO.getPrice())
                    .build();

            this.commandDispatcher.send(createProductCommand);
        }

        return ResponseEntity.ok("");
    }


    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody List<ProductDTO> productDTOS) throws Exception {
        for (ProductDTO productDTO : productDTOS) {
            final UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                    .id(productDTO.getId())
                    .productName(productDTO.getProductName())
                    .quantity(productDTO.getQuantity())
                    .price(productDTO.getPrice())
                    .build();
            this.commandDispatcher.send(updateProductCommand);
        }
        return ResponseEntity.ok("");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestBody List<ProductDTO> productDTOS) throws Exception {
        for (ProductDTO productDTO : productDTOS) {
            final DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder()
                    .id(productDTO.getId())
                    .productName(productDTO.getProductName())
                    .quantity(productDTO.getQuantity())
                    .price(productDTO.getPrice())
                    .build();

            this.commandDispatcher.send(deleteProductCommand);
        }
        return ResponseEntity.ok("");
    }

}
