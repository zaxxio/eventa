package org.wsd.app.config;

import org.eventa.core.interceptor.CommandInterceptorRegisterer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.wsd.app.interceptor.ProductCommandInterceptor;
import org.wsd.app.repository.ProductRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = {ProductRepository.class})
public class EventaConfig {

    private final ProductCommandInterceptor productCommandInterceptor;

    public EventaConfig(ProductCommandInterceptor productCommandInterceptor) {
        this.productCommandInterceptor = productCommandInterceptor;
    }

    @Bean
    public CommandInterceptorRegisterer commandInterceptorRegisterer() {
        CommandInterceptorRegisterer commandInterceptorRegisterer = new CommandInterceptorRegisterer();
        commandInterceptorRegisterer.register(productCommandInterceptor);
        return commandInterceptorRegisterer;
    }

}
