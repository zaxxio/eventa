package org.eventa.core.aggregates;

import lombok.Getter;
import lombok.Setter;
import org.eventa.core.registry.EventSourcingHandlerRegistry;
import org.eventa.core.streotype.Snapshot;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.eventa.core.events.BaseEvent;

import java.lang.reflect.Method;
import java.util.*;


public abstract class AggregateRoot implements ApplicationContextAware {

    @Getter
    protected UUID id;
    @Setter
    @Getter
    protected int version = -1;
    private ApplicationContext applicationContext;
    private final List<BaseEvent> changes = new ArrayList<>();
    private final int snapshotInterval;

    public AggregateRoot() {
        Snapshot snapshotAnnotation = this.getClass().getAnnotation(Snapshot.class);
        if (snapshotAnnotation != null) {
            this.snapshotInterval = snapshotAnnotation.interval();
        } else {
            this.snapshotInterval = 100;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<BaseEvent> getUncommittedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    public void replayEvents(List<BaseEvent> events) {
        for (BaseEvent event : events) {
            apply(event, false);
        }
    }

    protected void apply(BaseEvent baseEvent) {
        apply(baseEvent, true);
    }

    protected void apply(BaseEvent baseEvent, boolean isNewEvent) {
        handleEvent(baseEvent);
        if (isNewEvent) {
            changes.add(baseEvent);
            if (version % snapshotInterval == 0) {
                System.out.println("Snap");
            }
        }
        this.version++;
    }


    private void handleEvent(BaseEvent baseEvent) {
        EventSourcingHandlerRegistry registry = applicationContext.getBean(EventSourcingHandlerRegistry.class);
        Class<?> clazz = baseEvent.getClass();
        Method handler = registry.getHandler(clazz);
        if (handler != null && handler.getDeclaringClass().isAssignableFrom(this.getClass())) {
            try {
                handler.invoke(this, baseEvent);
            } catch (Exception e) {
                throw new RuntimeException("Failed to handle event", e);
            }
        }
    }


}
