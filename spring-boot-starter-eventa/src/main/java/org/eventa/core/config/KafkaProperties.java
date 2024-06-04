package org.eventa.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String eventStoreName;
    private String bootstrapServers;
    private String[] trustedPackages;
}
