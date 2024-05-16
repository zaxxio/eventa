package org.eventa.core.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;


@Getter
@Setter
@ConfigurationProperties(prefix = "eventa")
public class EventaProperties {
    @NestedConfigurationProperty
    private MongoDBProperties mongodb;
    @NestedConfigurationProperty
    private KafkaProperties kafka;
}
