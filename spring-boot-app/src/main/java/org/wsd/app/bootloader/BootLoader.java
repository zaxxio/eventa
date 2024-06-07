package org.wsd.app.bootloader;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wsd.app.commands.product.CreateProductCommand;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootLoader implements CommandLineRunner {

    private final CommandDispatcher commandDispatcher;

    @Override
    public void run(String... args) throws Exception {

//        final Faker faker = new Faker();
//
//        for (int i = 0; i < 10_000; i++) {
//
//            final CreateProductCommand createProductCommand = CreateProductCommand.builder()
//                    .id(UUID.randomUUID())
//                    .productName(faker.funnyName().name())
//                    .quantity((double) faker.number().randomDigitNotZero())
//                    .price((double)faker.number().randomDigitNotZero())
//                    .build();
//
//            this.commandDispatcher.send(createProductCommand);
//        }
    }
}
