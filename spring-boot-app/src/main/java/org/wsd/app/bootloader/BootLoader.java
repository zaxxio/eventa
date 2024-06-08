package org.wsd.app.bootloader;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wsd.app.commands.product.CreateProductCommand;
import org.wsd.app.commands.product.UpdateProductCommand;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootLoader implements CommandLineRunner {
    private final CommandDispatcher commandDispatcher;

    @Override
    public void run(String... args) throws Exception {
        /*UUID productId = UUID.randomUUID();
        Faker faker = new Faker();

        final CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .id(productId)
                .productName(faker.gameOfThrones().character())
                .quantity((double) faker.number().randomDigitNotZero())
                .threadName(Thread.currentThread().getName())
                .price((double) faker.number().randomDigitNotZero())
                .build();

        final String id = this.commandDispatcher.send(createProductCommand);

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                final UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                        .id(productId)
                        .productName(faker.gameOfThrones().character())
                        .quantity((double) faker.number().randomDigitNotZero())
                        .price((double) faker.number().randomDigitNotZero())
                        .build();
                try {
                    this.commandDispatcher.send(updateProductCommand);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                final UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                        .id(productId)
                        .productName(faker.gameOfThrones().character())
                        .quantity((double) faker.number().randomDigitNotZero())
                        .price((double) faker.number().randomDigitNotZero())
                        .build();
                try {
                    this.commandDispatcher.send(updateProductCommand);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();*/
    }
}
