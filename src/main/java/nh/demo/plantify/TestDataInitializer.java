package nh.demo.plantify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Profile("import-testdata")
@Component
class TestDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestDataInitializer.class);

    private final JdbcClient jdbcClient;
    private final DataSource dataSource;

    public TestDataInitializer(JdbcClient jdbcClient, DataSource dataSource) {
        this.jdbcClient = jdbcClient;
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Prüfen, ob bereits Daten vorhanden sind
        Integer ownerCount = jdbcClient.sql("SELECT COUNT(*) FROM owner_schema.owners")
                .query(Integer.class)
                .single();

        if (ownerCount != null && ownerCount > 0) {
            log.info("Test data already exists, skipping initialization");
            return;
        }

        log.info("Initializing test data from SQL script...");

        try (Connection connection = dataSource.getConnection()) {
            ClassPathResource resource = new ClassPathResource("testdata.sql");
            ScriptUtils.executeSqlScript(connection, resource);
            log.info("Test data initialization completed!");
        } catch (Exception e) {
            log.error("Failed to initialize test data", e);
            throw new RuntimeException("Failed to initialize test data", e);
        }
    }
}
