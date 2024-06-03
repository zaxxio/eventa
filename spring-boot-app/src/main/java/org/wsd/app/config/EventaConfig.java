package org.wsd.app.config;

import org.eventa.core.interceptor.CommandInterceptorRegisterer;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wsd.app.interceptor.ProductCommandInterceptor;

@Configuration
public class EventaConfig {

    @Autowired
    private ProductCommandInterceptor productCommandInterceptor;

    @Bean
    public CommandInterceptorRegisterer commandInterceptorRegisterer() {
        CommandInterceptorRegisterer commandInterceptorRegisterer = new CommandInterceptorRegisterer();
        commandInterceptorRegisterer.register(productCommandInterceptor);
        return commandInterceptorRegisterer;
    }

}
