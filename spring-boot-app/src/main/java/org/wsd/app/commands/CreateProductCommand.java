package org.wsd.app.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.wsd.core.commands.BaseCommand;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class CreateProductCommand extends BaseCommand {
    private UUID id;
}
