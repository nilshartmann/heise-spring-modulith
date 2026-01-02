package nh.demo.plantify;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class PlantifyModuleTest {

    @Test
    void verifyModules() {

        var modules = ApplicationModules.of(PlantifyApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
    }

    @Test
    void writeDocumentationSnippets() {
        var modules = ApplicationModules.of(PlantifyApplication.class);
        new Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
            .writeAggregatingDocument();
    }

}
