package org.wsd.app.websocket.event;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class ConnectionEstablishedEvent {
    private UUID id;
}
