package org.wsd.core.events;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.wsd.core.messages.Message;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent extends Message {
    private int version;
}
