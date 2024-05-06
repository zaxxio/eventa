package org.wsd.core.eventstore;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.wsd.core.events.BaseEvent;

import java.util.Date;

@Getter
@Setter
@Builder
@Document(collection = "events")
public class EventModel {
    @Id
    private String id;
    @Indexed
    private String aggregateIdentifier;
    private String aggregateType;
    private String eventType;
    private Integer version;
    private BaseEvent baseEvent;
    private Date timestamp;
}
