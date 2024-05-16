package org.wsd.app.projection;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.wsd.app.query.ItemQuery;
import org.eventa.core.streotype.ProjectionGroup;
import org.eventa.core.streotype.QueryHandler;

import java.util.List;

@Log4j2
@Service
@ProjectionGroup
public class ItemProjection {
    @QueryHandler
    public List<Integer> handle(ItemQuery itemQuery) {
        return List.of(1, 2, 3, 4, 5);
    }
}
