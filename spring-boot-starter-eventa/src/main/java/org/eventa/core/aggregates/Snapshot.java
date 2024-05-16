package org.eventa.core.aggregates;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class Snapshot {
    private final UUID id;
    private final int version;
    private final Object state;
}