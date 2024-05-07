package org.wsd.app.bootloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.eventa.core.gateway.CommandDispatcher;

@Component
public class BootLoader implements CommandLineRunner {
    @Autowired
    private CommandDispatcher commandDispatcher;
    @Override
    public void run(String... args) throws Exception {
        /*for (int i = 0; i < 10000; i++) {
            final CreateProductCommand createProductCommand = org.wsd.app.commands.CreateProductCommand.builder()
                    .id(UUID.randomUUID())
                    .productName("Iphone 15")
                    .quantity(100d)
                    .price(1200d)
                    .build();
            commandDispatcher.send(createProductCommand);
        }*/
    }
}
