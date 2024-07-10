package org.eventa.core.bus.callback;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommandResult<T> {
    private boolean success;
    private T result;
    private String errorMessage;
}