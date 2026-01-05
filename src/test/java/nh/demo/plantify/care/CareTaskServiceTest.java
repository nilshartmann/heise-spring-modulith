package nh.demo.plantify.care;

import nh.demo.plantify.TestcontainersConfiguration;
import nh.demo.plantify.care.suggestion.CareTaskType;
import nh.demo.plantify.plant.PlantRegisteredEvent;
import nh.demo.plantify.plant.PlantType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
@Import(TestcontainersConfiguration.class)
class CareTaskServiceTest {

    static UUID plantId = UUID.fromString("8d0eba98-17aa-4ba3-ad55-d60e3f044c42");

    @Autowired
    CareTaskRepository careTaskRepository;

    @Autowired
    JdbcClient jdbcClient;

    @Test
    void test_creates_initial_task_when_new_plant_is_registered(Scenario scenario) {
        scenario
            .publish(
                new PlantRegisteredEvent(
                    plantId,
                    UUID.fromString("b34e3c67-2cc9-4708-a99c-738b4bda4f92"),
                    PlantType.SUMMER_FLOWERS,
                    "Garden"
                )
            )
            .andWaitForEventOfType(InitialCareTasksCreatedEvent.class)
            .matching(e -> e.plantId().equals(plantId))
            .toArrive();

        // Default Factory legt zwei Tasks an
        assertThat(careTaskRepository.findAll()).satisfiesExactlyInAnyOrder(
            t1 -> {
                assertThat(t1.getPlantId()).isEqualTo(plantId);
                assertThat(t1.getType()).isEqualTo(CareTaskType.WATERING);
            },
            t2 -> {
                assertThat(t2.getPlantId()).isEqualTo(plantId);
                assertThat(t2.getType()).isEqualTo(CareTaskType.REPOTTING);
            }
        );
    }

    @Test
    void only_tables_for_required_modules_are_created() {
        // Nur Demo: "Beweis", das Spring Modulith nur die DB für das einzelne Modul startet
        //
        // 'plant'-Modul muss nicht gestartet werden, weil es zwar eine Code-Abhängigkeit gibt (PlantRegisteredEvent),
        // aber keine Service-Abhängigkeit
        var schemaName = jdbcClient
            .sql("select nspname as schema_name from pg_namespace p, pg_authid pa where p.nspowner = pa.oid and pa.rolname = :myUserName and p.nspname not like 'pg_%' and p.nspname != 'information_schema'")
            .param("myUserName", TestcontainersConfiguration.TEST_DB_USERNAME)
            .query(String.class)
            .single();



        assertThat(schemaName).isEqualTo("care_schema");
    }


}