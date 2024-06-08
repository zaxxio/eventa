package org.eventa.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${eventa.kafka.event-bus}")
    private String eventBus;

    @Value("${eventa.kafka.command-bus}")
    private String commandBus;

    @Bean
    public NewTopic eventBusTopic() {
        return TopicBuilder.name(eventBus)
                .partitions(3)
                .config("cleanup.policy", "delete")
                .build();
    }


/*    @Bean
    public NewTopic commandBusTopic() {
        return TopicBuilder.name(commandBus)
                .partitions(3)
                .build();
    }*/

}
