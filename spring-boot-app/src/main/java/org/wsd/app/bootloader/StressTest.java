package org.wsd.app.bootloader;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.eventa.core.dispatcher.CommandDispatcher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wsd.app.commands.product.CreateProductCommand;
import org.wsd.app.commands.product.UpdateProductCommand;
import org.wsd.app.controller.ProductCommandController;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StressTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
