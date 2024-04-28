package org.wsd.core.bootloader;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wsd.core.example.commands.CreateAccountCommand;
import org.wsd.core.example.commands.DepositCashCommand;
import org.wsd.core.gateway.CommandGateway;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Bootloader implements CommandLineRunner {
    private final CommandGateway commandGateway;

    @Override
    public void run(String... args) throws Exception {

        Instant start = Instant.now();
        UUID uuid = UUID.randomUUID();

        CreateAccountCommand createAccountCommand = CreateAccountCommand.builder()
                .id(uuid)
                .name("Partha Sutradhar")
                .balance(150000d)
                .build();
        commandGateway.send(createAccountCommand);


        DepositCashCommand depositCashCommand = DepositCashCommand.builder()
                .id(uuid)
                .name("Partha")
                .balance(120000d)
                .build();

        commandGateway.send(depositCashCommand);

        Instant end = Instant.now();
        System.out.println("Time : " + Duration.between(start, end).toMillis());

    }
}
