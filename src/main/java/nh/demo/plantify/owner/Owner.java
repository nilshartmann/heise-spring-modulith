package nh.demo.plantify.owner;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "owners", schema = "owner_schema")
public class Owner {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    protected Owner() {}

    public Owner(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
