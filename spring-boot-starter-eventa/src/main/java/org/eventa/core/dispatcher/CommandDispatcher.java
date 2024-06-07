package org.eventa.core.dispatcher;

import org.eventa.core.commands.BaseCommand;

import java.util.concurrent.CompletableFuture;

public interface CommandDispatcher {
    <T extends BaseCommand> String send(T command) throws Exception;
}
