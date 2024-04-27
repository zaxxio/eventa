package org.wsd.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EventaProperties.class)
public class EventaAutoConfiguration {

}
