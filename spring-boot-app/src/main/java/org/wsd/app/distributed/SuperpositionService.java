package org.wsd.app.distributed;

import lombok.extern.log4j.Log4j2;
import org.eventa.core.streotype.Leader;
import org.eventa.core.streotype.NotLeader;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SuperpositionService {


    @Leader
    public void methodA(){
        log.info("I'm a Leader.");
    }

    @NotLeader
    public void methodB() {
        log.info("I'm not a Leader.");
    }

}
