package nh.demo.plantify;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DurationTest {

    @Test
    void test_duration() {
        LocalDate start = LocalDate.parse("2025-10-27");
        LocalDate end = LocalDate.parse("2025-11-02");
        var storageTime = ChronoUnit.DAYS.between(start, end);
        assertThat(storageTime).isEqualTo(6);
    }
}
