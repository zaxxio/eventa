package org.wsd.core.example.aggregates;

import org.wsd.core.aggregate.AggregateRoot;
import org.wsd.core.example.commands.CreateAccountCommand;
import org.wsd.core.example.commands.DepositCashCommand;
import org.wsd.core.example.events.AccountCreatedEvent;
import org.wsd.core.example.events.CashDepositedEvent;
import org.wsd.core.streotype.*;

@Aggregate
public class AccountAggregate extends AggregateRoot {

    private String accountHolder;
    private Double balance;

    @CommandHandler(payload = CreateAccountCommand.class)
    public void handle(@Command CreateAccountCommand createAccountCommand) {
        apply(
                AccountCreatedEvent.builder()
                        .id(createAccountCommand.getId())
                        .name(createAccountCommand.getName())
                        .balance(createAccountCommand.getBalance())
                        .build()
        );
    }

    @EventSourcingHandler(payload = AccountCreatedEvent.class)
    public void on(@EventSourced AccountCreatedEvent accountCreatedEvent) {
        super.id = accountCreatedEvent.getId();
        this.accountHolder = accountCreatedEvent.getName();
        this.balance = accountCreatedEvent.getBalance();
    }


    @CommandHandler(payload = DepositCashCommand.class)
    public void handle(@Command DepositCashCommand depositCashCommand) {
        apply(
                CashDepositedEvent.builder()
                        .id(depositCashCommand.getId())
                        .name(depositCashCommand.getName())
                        .balance(depositCashCommand.getBalance())
                        .build()
        );
    }

    @EventSourcingHandler(payload = CashDepositedEvent.class)
    public void on(@EventSourced CashDepositedEvent cashDepositedEvent) {
        super.id = cashDepositedEvent.getId();
        this.accountHolder = cashDepositedEvent.getName();
        this.balance += cashDepositedEvent.getBalance();
    }

}
