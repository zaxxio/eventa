package org.eventa.core.bus;

import org.eventa.core.commands.BaseCommand;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface CommandBus {
    CompletableFuture<?> sendAndWait(BaseCommand baseCommand, int time,TimeUnit timeUnit);
}
