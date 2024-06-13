package org.wsd.app.distributed;

import lombok.extern.log4j.Log4j2;
import org.eventa.core.streotype.Leader;
import org.eventa.core.streotype.NotLeader;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SuperpositionService {


    @Leader
    public void methodA(){
        log.info("Microservice 1.");
    }

    @NotLeader
    public void methodB() {
        log.info("I'm not a Leader.");
    }

}
