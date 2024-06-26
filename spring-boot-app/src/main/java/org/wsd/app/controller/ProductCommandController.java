package org.wsd.app.controller;

import lombok.RequiredArgsConstructor;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.eventa.core.streotype.DistributedLock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wsd.app.commands.product.CreateProductCommand;
import org.wsd.app.commands.product.DeleteProductCommand;
import org.wsd.app.commands.product.UpdateProductCommand;
import org.wsd.app.model.ProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductCommandController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    @DistributedLock(value = "createProduct", timeout = 5, timeUnit = TimeUnit.SECONDS)
    public ResponseEntity<?> createProduct(@RequestBody List<ProductDTO> productDTOS) throws Exception {
        final List<String> processed = new ArrayList<>();
        for (ProductDTO productDTO : productDTOS) {
            final CreateProductCommand createProductCommand = CreateProductCommand.builder()
                    .id(UUID.randomUUID())
                    .productName(productDTO.getProductName())
                    .quantity(productDTO.getQuantity())
                    .price(productDTO.getPrice())
                    .build();
            final String id = this.commandDispatcher.send(createProductCommand);
            processed.add(id);
        }
        return ResponseEntity.ok(processed);
    }


    @PutMapping
    @DistributedLock(value = "updateProduct", timeout = 5, timeUnit = TimeUnit.SECONDS)
    public ResponseEntity<?> updateProduct(@RequestBody List<ProductDTO> productDTOS) throws Exception {
        final List<String> processed = new ArrayList<>();
        for (ProductDTO productDTO : productDTOS) {
            final UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                    .id(productDTO.getId())
                    .productName(productDTO.getProductName())
                    .quantity(productDTO.getQuantity())
                    .price(productDTO.getPrice())
                    .build();
            String id = this.commandDispatcher.send(updateProductCommand);
            processed.add(id);
        }
        return ResponseEntity.ok(processed);
    }

    @DeleteMapping
    @DistributedLock(value = "deleteProduct", timeout = 5, timeUnit = TimeUnit.SECONDS)
    public ResponseEntity<?> deleteProduct(@RequestBody List<ProductDTO> productDTOS) throws Exception {
        List<String> processed = new ArrayList<>(100);
        for (ProductDTO productDTO : productDTOS) {
            final DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder()
                    .id(productDTO.getId())
                    .productName(productDTO.getProductName())
                    .quantity(productDTO.getQuantity())
                    .price(productDTO.getPrice())
                    .build();

            String id = this.commandDispatcher.send(deleteProductCommand);
            processed.add(id);
        }
        return ResponseEntity.ok(processed);
    }

}
