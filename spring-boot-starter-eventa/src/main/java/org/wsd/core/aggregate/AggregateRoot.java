package org.wsd.core.aggregate;

import java.util.LinkedList;
import java.util.List;

public abstract class Aggregate {
    private final List<Object> changes = new LinkedList<>();
    protected void apply()
}
