package nh.demo.plantify.care;


import nh.demo.plantify.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.AssertablePublishedEvents;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
@Sql(value = "/test-care-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CareControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void sends_care_task_completed_event_when_completing_a_care_task(AssertablePublishedEvents events) {
        MvcTestResult testResult = mockMvcTester.post().uri("/api/care-tasks/{id}/complete", "20caccb2-d48e-4f59-817a-1106a02da986")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(testResult).hasStatus(HttpStatus.OK);

        assertThat(events).contains(CareTaskCompletedEvent.class)
            .matching(CareTaskCompletedEvent::careTaskId, UUID.fromString("20caccb2-d48e-4f59-817a-1106a02da986"))
            .matching(CareTaskCompletedEvent::plantId, UUID.fromString("db7fee3a-9668-4bab-9679-dafe21a4f7c2"))
        ;

    }

    @Test
    void sends_care_task_completed_and_deactivated_event_when_completing_a_onetime_care_task(AssertablePublishedEvents events) {
        MvcTestResult testResult = mockMvcTester.post().uri("/api/care-tasks/{id}/complete", "e572cc7f-6a0f-46e8-90c5-54375f33cf8b")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(testResult).hasStatus(HttpStatus.OK);

        assertThat(events).contains(CareTaskCompletedEvent.class)
            .matching(CareTaskCompletedEvent::careTaskId, UUID.fromString("e572cc7f-6a0f-46e8-90c5-54375f33cf8b"))
            .matching(CareTaskCompletedEvent::plantId, UUID.fromString("03f5fd02-7cd5-44b6-8b88-762dffe35475"))
            .and()
            .contains(CareTaskDeactivatedEvent.class)
            .matching(CareTaskDeactivatedEvent::careTaskId, UUID.fromString("e572cc7f-6a0f-46e8-90c5-54375f33cf8b"))
            .matching(CareTaskDeactivatedEvent::plantId, UUID.fromString("03f5fd02-7cd5-44b6-8b88-762dffe35475"))
        ;
    }
}
