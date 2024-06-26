package org.wsd.app.controller;

import lombok.RequiredArgsConstructor;
import org.eventa.core.query.ResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wsd.app.domain.Product;
import org.wsd.app.push.PushMessageEvent;
import org.wsd.app.query.FindByProductIdQuery;
import org.wsd.app.websocket.producer.PushEventService;

import java.util.UUID;

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
