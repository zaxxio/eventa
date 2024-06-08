package org.eventa.core.saga;

import lombok.Data;
import org.eventa.core.events.BaseEvent;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class SagaState {
    private UUID id;
    private UUID sagaId;
    private String stepName;
    private Object payload;
}
