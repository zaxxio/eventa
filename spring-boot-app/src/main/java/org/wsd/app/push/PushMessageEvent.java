package org.wsd.app.push;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class PushMessageEvent {
    private UUID id;
    private String message;
}
