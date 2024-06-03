package org.wsd.app.interceptor;

import org.eventa.core.commands.BaseCommand;
import org.eventa.core.interceptor.CommandInterceptor;
import org.springframework.stereotype.Component;
import org.wsd.app.commands.CreateProductCommand;

@Component
public class ProductCommandInterceptor implements CommandInterceptor {

    @Override
    public void preHandle(BaseCommand command) {
        if (command instanceof CreateProductCommand) {
            // change or logical processing pre-processing
        }
    }

    @Override
    public void postHandle(BaseCommand command) {
        if (command instanceof  CreateProductCommand) {
            // change or logical processing post-processing
        }
    }
}
