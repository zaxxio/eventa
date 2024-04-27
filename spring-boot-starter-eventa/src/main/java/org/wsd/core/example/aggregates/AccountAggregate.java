package org.wsd.springbootapp.aggregates;

import org.springframework.stereotype.Service;
import org.wsd.springbootapp.commands.CreateAccountCommand;
import org.wsd.springbootapp.events.AccountCreatedEvent;
import org.wsd.core.streotype.Aggregate;
import org.wsd.core.streotype.AggregateId;
import org.wsd.core.streotype.CommandHandler;
import org.wsd.core.streotype.EventSourcingHandler;

import java.util.UUID;

@Aggregate
public class AccountAggregate {

    @AggregateId
    private UUID id;

    @CommandHandler(payload = CreateAccountCommand.class)
    public void handle(CreateAccountCommand createAccountCommand) {

    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {

    }
}
