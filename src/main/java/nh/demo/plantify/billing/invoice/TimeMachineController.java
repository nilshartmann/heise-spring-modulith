package nh.demo.plantify.billing.invoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.moments.support.TimeMachine;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/timemachine")
class TimeMachineController {

    private static final Logger log = LoggerFactory.getLogger( TimeMachineController.class );

    private final TimeMachine timeMachine;

    TimeMachineController(TimeMachine timeMachine) {
        this.timeMachine = timeMachine;
    }

    @PostMapping("/{shiftBy}")
    String settime(@PathVariable long shiftBy) {
       timeMachine.shiftBy(Duration.ofDays(shiftBy));

       var newNow = timeMachine.now().toString();

       log.info("New now: '{}'", newNow);

       return newNow;
    }

}
