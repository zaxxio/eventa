package org.eventa.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic baseEventTopic() {
        return TopicBuilder.name("baseEvent")
                .partitions(3)
                .build();
    }

}
