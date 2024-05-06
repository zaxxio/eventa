package org.wsd.core.gateway.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.wsd.core.gateway.QueryDispatcher;
import org.wsd.core.query.ResponseType;
import org.wsd.core.registry.QueryHandlerRegistry;
import org.wsd.core.streotype.QueryHandler;

import java.lang.reflect.Method;

@Service
@RequiredArgsConstructor
public class QueryDispatcherImpl implements QueryDispatcher {
    private final QueryHandlerRegistry queryHandlerRegistry;
    private final ApplicationContext applicationContext;

    @Override
    public <Q, R> R dispatch(Q query, ResponseType<R> responseType) {
        Method queryMethod = queryHandlerRegistry.getHandler(query.getClass());
        if (queryMethod == null) {
            throw new RuntimeException("No handler found for query: " + query.getClass().getName());
        }
        try {
            Object handlerBean = applicationContext.getBean(queryMethod.getDeclaringClass());
            Object result = queryMethod.invoke(handlerBean, query);
            return responseType.convert(result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke handler for query: " + query.getClass().getName(), e);
        }
    }
}
