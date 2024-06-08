package org.eventa.core.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eventa.core.interceptor.CommandInterceptorRegisterer;
import org.eventa.core.repository.EventStoreRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

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
public class EventaAutoConfiguration implements BeanFactoryAware {


    private final EventaProperties eventaProperties;
    private BeanFactory beanFactory;

    @PostConstruct
    public void postConstruct() {

    }

    @Bean
    public TaskExecutor eventaTaskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setThreadNamePrefix("eventa-group");
        taskExecutor.setConcurrencyLimit(Runtime.getRuntime().availableProcessors() / 2);
        return taskExecutor;
    }


    @Bean
    @ConditionalOnMissingBean
    public CommandInterceptorRegisterer commandInterceptorRegisterer() {
        return new CommandInterceptorRegisterer();
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
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
