package org.wsd.core.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Log4j2
@AutoConfiguration
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(EventaProperties.class)
@ComponentScan(basePackages = "org.wsd.core")
public class EventaAutoConfiguration {

    @PostConstruct
    public void EventaAutoConfiguration() {
        log.info("Eventa Auto Configuration.");
    }

}
