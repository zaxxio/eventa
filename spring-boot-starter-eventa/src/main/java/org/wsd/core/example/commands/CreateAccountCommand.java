package org.wsd.core.example.commands;

import lombok.Builder;
import lombok.Data;
import org.wsd.core.streotype.Command;

import java.util.UUID;

@Data
@Builder
@Command
public class CreateAccountCommand {
    private UUID id;
    private String name;
    private Double balance;
}
