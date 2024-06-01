package org.eventa.core.interceptor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandInterceptorRegisterer {

    private List<CommandInterceptor> commandInterceptors;

    public CommandInterceptorRegisterer() {
        this.commandInterceptors = new ArrayList<>();
    }

    public synchronized void register(CommandInterceptor commandInterceptor) {
        this.commandInterceptors.add(commandInterceptor);
    }

    public List<CommandInterceptor> getCommandInterceptors() {
        return commandInterceptors;
    }
}
