package nh.demo.plantify.billing.invoice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.moments.support.TimeMachine;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

// - Ins Projekt kopieren (Modul: Invoice, ist aber eigentlich egal)
//  - application.properties aktualisieren
//    spring.modulith.moments.enable-time-machine=true
// - Neustart + Konsole leeren
// - Konsole + Terminal parallel öffnen
// -     http POST localhost:8080/api/time-machine/20
@RestController
class TimeShifter {

    private static final Logger log = LoggerFactory.getLogger( TimeShifter.class );

    private final TimeMachine timeMachine;

    TimeShifter(TimeMachine timeMachine) {
        this.timeMachine = timeMachine;
    }

    @PostMapping("/api/time-machine/{days}")
    void shiftDays(@PathVariable int days) {
        timeMachine.shiftBy(Duration.ofDays(days));

        log.info("Shifted by {} days. Now: {}", days, timeMachine.now());
    }
}
