package org.wsd.core.gateway;

import org.wsd.core.commands.BaseCommand;

public interface CommandDispatcher {
    <T extends BaseCommand> void send(T command);
}
