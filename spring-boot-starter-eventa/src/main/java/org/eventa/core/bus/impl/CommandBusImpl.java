package org.eventa.core.bus.impl;

import org.eventa.core.bus.CommandBus;
import org.eventa.core.bus.callback.CommandResult;
import org.eventa.core.commands.BaseCommand;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class CommandBusImpl implements CommandBus {

    private final Map<String, CompletableFuture<CommandResult<Object>>> concurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public CompletableFuture<?> sendAndWait(BaseCommand baseCommand, int time, TimeUnit timeUnit) {
        return null;
    }
}
