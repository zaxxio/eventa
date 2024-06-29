package org.wsd.app.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.wsd.app.websocket.event.ConnectionEstablishedEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PushMessageEventSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        final UUID uuid = UUID.randomUUID();
        ConnectionEstablishedEvent event = new ConnectionEstablishedEvent();
        event.setId(uuid);

        final String payload = objectMapper.writeValueAsString(event);

        session.sendMessage(new TextMessage(payload));
        this.sessionManager.addSession(uuid, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionManager.remove(session);
    }
}
