package org.wsd.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.wsd.core.registry.CommandHandlerRegistry;

@AutoConfiguration
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(EventaProperties.class)
public class EventaAutoConfiguration {
}
