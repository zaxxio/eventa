package org.wsd.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.wsd.core.config.EventaAutoConfiguration;

@SpringBootTest
@ContextConfiguration(classes = EventaAutoConfiguration.class)
public class EventaTest {

    @Test
    public void eventaTest() {

    }

}
