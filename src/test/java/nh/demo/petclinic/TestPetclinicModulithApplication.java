package nh.demo.petclinic;

import org.springframework.boot.SpringApplication;

public class TestPetclinicModulithApplication {

    public static void main(String[] args) {
        SpringApplication.from(PetclinicModulithApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
