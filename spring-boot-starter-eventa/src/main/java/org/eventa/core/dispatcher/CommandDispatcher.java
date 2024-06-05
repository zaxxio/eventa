package org.eventa.core.dispatcher;

import org.eventa.core.commands.BaseCommand;

public interface CommandDispatcher {
    <T extends BaseCommand> void send(T command) throws Exception;
}
