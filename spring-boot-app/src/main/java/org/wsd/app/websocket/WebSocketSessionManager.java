package org.wsd.app.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {
    private Map<UUID, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    public void addSession(UUID id, WebSocketSession session) {
        this.sessionMap.put(id, session);
    }

    public void remove(WebSocketSession session) {
        this.sessionMap.values().remove(session);
    }

    public WebSocketSession getSession(UUID id) {
        return this.sessionMap.get(id);
    }
}
