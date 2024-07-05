package org.eventa.core.bus;

import org.eventa.core.commands.BaseCommand;

import java.util.concurrent.CompletableFuture;

public interface CommandBus {
    CompletableFuture<?> sendAndWait(BaseCommand baseCommand);
}
