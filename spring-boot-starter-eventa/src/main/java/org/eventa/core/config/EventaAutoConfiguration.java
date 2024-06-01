package org.eventa.core.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eventa.core.repository.EventStoreRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.concurrent.Executor;

@Log4j2
@AutoConfiguration
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(MongoAutoConfiguration.class)
@EnableConfigurationProperties(EventaProperties.class)
@ConfigurationPropertiesScan
@EnableMongoRepositories(basePackageClasses = {EventStoreRepository.class})
@ComponentScan(basePackages = "org.eventa.core")
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
@EnableAsync
public class EventaAutoConfiguration {


    private final EventaProperties eventaProperties;

    @PostConstruct
    public void postConstruct() {

    }

//    @Bean
//    public RequestMappingHandlerMapping customRequestMappingHandlerMapping() {
//        return new ApiVersionRequestMappingHandlerMapping();
//    }

    @Bean
    public MongoTransactionManager eventaMongoDBtransactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }


    /*@Bean
    public SmartInstantiationAwareBeanPostProcessor customConstructorResolver() {
        return new SmartInstantiationAwareBeanPostProcessor() {
            @Override
            public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
                // Check if the bean class is one of your aggregate roots
                if (AggregateRoot.class.isAssignableFrom(beanClass)) {
                    // Try to find a constructor that takes a BaseCommand subclass
                    for (Constructor<?> constructor : beanClass.getConstructors()) {
                        for (Class<?> paramType : constructor.getParameterTypes()) {
                            if (BaseCommand.class.isAssignableFrom(paramType)) {
                                return new Constructor<?>[]{constructor};
                            }
                        }
                    }
                }
                return null;
            }
        };
    }*/

}
