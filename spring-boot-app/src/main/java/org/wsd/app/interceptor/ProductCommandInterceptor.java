package org.wsd.app.interceptor;

import lombok.extern.log4j.Log4j2;
import org.eventa.core.commands.BaseCommand;
import org.eventa.core.interceptor.CommandInterceptor;
import org.springframework.stereotype.Component;
import org.wsd.app.commands.CreateProductCommand;

@Log4j2
@Component
public class ProductCommandInterceptor implements CommandInterceptor {

    @Override
    public void preHandle(BaseCommand command) {
        if (command instanceof CreateProductCommand) {
            // change or logical processing pre-processing
            Double price = ((CreateProductCommand) command).getPrice();
            double updatedPrice = price - 1;
            ((CreateProductCommand) command).setPrice(updatedPrice);
            log.info("Applied interceptor on : {}", command);
        }
    }

    @Override
    public void postHandle(BaseCommand command) {
        if (command instanceof CreateProductCommand) {
            // change or logical processing post-processing
        }
    }
}
