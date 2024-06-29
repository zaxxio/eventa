package org.wsd.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wsd.app.push.PushMessageEvent;
import org.wsd.app.websocket.producer.PushEventService;

@RestController
@RequestMapping("/api/push")
@RequiredArgsConstructor
public class PushEventController {
    private final PushEventService pushEventService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProductById(PushMessageEvent pushMessageEvent) {
        this.pushEventService.pushEvent(pushMessageEvent);
        return ResponseEntity.ok(pushMessageEvent);
    }
}
