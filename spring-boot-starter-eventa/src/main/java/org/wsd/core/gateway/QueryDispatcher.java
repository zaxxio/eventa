package org.wsd.core.gateway;

import org.wsd.core.query.ResponseType;

public interface QueryDispatcher {
    <Q, R> R dispatch(Q query, ResponseType<R> responseType);
}

