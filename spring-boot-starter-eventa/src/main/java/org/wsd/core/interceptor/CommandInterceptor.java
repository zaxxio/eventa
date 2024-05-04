package org.wsd.core.interceptor;

import org.wsd.core.commands.BaseCommand;

public interface CommandInterceptor {
    void preHandle(BaseCommand command);
    void postHandle(BaseCommand command);
}
