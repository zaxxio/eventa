package org.wsd.core.aggregate;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.wsd.core.registry.EventSourcingHandlerRegistry;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class AggregateRoot implements ApplicationContextAware {

    private final List<Object> changes = new LinkedList<>();
    private ApplicationContext applicationContext;
    protected UUID id;
    protected Integer version = -1;

    protected void apply(Object event) {
        handleEvent(event);
        changes.add(event);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<Object> getUncommitedChanges() {
        return changes;
    }

    public void markChangesAsCommited() {
        changes.clear();
    }

    public void replayEvents(List<Object> events) {
        events.forEach(this::apply);
        this.version = events.size();
    }

    private void handleEvent(Object event) {
        EventSourcingHandlerRegistry registry = applicationContext.getBean(EventSourcingHandlerRegistry.class);
        Class<?> clazz = event.getClass();
        Method handler = registry.getHandler(clazz);
        if (handler != null && handler.getDeclaringClass().isAssignableFrom(this.getClass())) {
            try {
                handler.invoke(this, event);
            } catch (Exception e) {
                throw new RuntimeException("Failed to handle event", e);
            }
        }
    }
}
