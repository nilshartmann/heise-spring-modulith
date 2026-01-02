package nh.demo.plantify;

import org.springframework.boot.SpringApplication;

public class TestPlantifyApplication {

    public static void main(String[] args) {
        SpringApplication.from(PlantifyApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
