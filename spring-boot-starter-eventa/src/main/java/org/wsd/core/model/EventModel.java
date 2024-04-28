package org.wsd.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@Document(collection = "event_store")
public class EventModel {
    @Id
    @Indexed
    private String id;
    private Date timestamp;
    @Indexed
    private UUID aggregateIdentifier;
    private String aggregateType;
    private Integer version;
    private String eventType;
    private Object event;
}
