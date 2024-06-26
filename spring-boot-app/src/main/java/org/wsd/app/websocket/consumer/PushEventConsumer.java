package org.wsd.app.websocket.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.wsd.app.push.PushMessageEvent;
import org.wsd.app.websocket.WebSocketSessionManager;
import org.wsd.app.websocket.event.ConnectionEstablishedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PushEventConsumer {

    private final WebSocketSessionManager webSocketSessionManager;
    private final ObjectMapper objectMapper;

    @KafkaListener(topicPattern = "PushEvent", concurrency = "${eventa.kafka.concurrency}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(PushMessageEvent pushMessageEvent, @Header(KafkaHeaders.OFFSET) String offset, Acknowledgment ack) throws Exception {
        WebSocketSession session = webSocketSessionManager.getSession(pushMessageEvent.getId());
        if (session != null) {
            String payload = objectMapper.writeValueAsString(pushMessageEvent);
            session.sendMessage(new TextMessage(payload));
            ack.acknowledge();
        }
    }

}