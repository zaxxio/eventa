package org.wsd.app.bootloader;

import lombok.RequiredArgsConstructor;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootLoader implements CommandLineRunner {

    private final CommandDispatcher commandDispatcher;

    @Override
    public void run(String... args) throws Exception {
        /*final UUID productOneId = UUID.randomUUID();
        final Faker faker = new Faker();

        final CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .id(productOneId)
                .productName(faker.gameOfThrones().character())
                .quantity((double) faker.number().randomDigitNotZero())
                .threadName(Thread.currentThread().getName())
                .price((double) faker.number().randomDigitNotZero())
                .build();

        final String id = this.commandDispatcher.send(createProductCommand);

        for (int i = 0; i < 1000; i++) {
            this.commandDispatcher.send(CreateProductCommand.builder()
                    .id(UUID.randomUUID())
                    .productName(faker.gameOfThrones().character())
                    .quantity((double) faker.number().randomDigitNotZero())
                    .threadName(Thread.currentThread().getName())
                    .price((double) faker.number().randomDigitNotZero())
                    .build()
            );
        }


        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    final UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                            .id(productOneId)
                            .productName(faker.gameOfThrones().character())
                            .quantity((double) faker.number().randomDigitNotZero())
                            .price((double) faker.number().randomDigitNotZero())
                            .build();
                    try {
                        commandDispatcher.send(updateProductCommand);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }

        UUID productTwoId = UUID.randomUUID();

        final CreateProductCommand command = CreateProductCommand.builder()
                .id(productTwoId)
                .productName(faker.gameOfThrones().character())
                .quantity((double) faker.number().randomDigitNotZero())
                .threadName(Thread.currentThread().getName())
                .price((double) faker.number().randomDigitNotZero())
                .build();

        this.commandDispatcher.send(command);


        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 100; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    final UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                            .id(productTwoId)
                            .productName(faker.gameOfThrones().character())
                            .quantity((double) faker.number().randomDigitNotZero())
                            .price((double) faker.number().randomDigitNotZero())
                            .build();
                    try {
                        commandDispatcher.send(updateProductCommand);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }*/
    }
}
