package org.wsd.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wsd.app.model.ProductDTO;
import org.wsd.app.query.FindByProductIdQuery;
import org.wsd.app.query.ItemQuery;
import org.wsd.core.gateway.QueryDispatcher;
import org.wsd.core.query.ResponseType;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class QueryCommandHandler {
    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<?> getExample(UUID id) throws Exception {
        FindByProductIdQuery findByProductIdQuery = FindByProductIdQuery
                .builder()
                .productId(id)
                .build();

        ItemQuery itemQuery = ItemQuery.builder().build();
        List<Integer> result = queryDispatcher.dispatch(itemQuery, ResponseType.multipleInstancesOf(Integer.class));
        return ResponseEntity.ok(result);
    }
}
