package rest.reservoirapi.models.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Table
@Entity(name = "reservoir")
public class Reservoir extends BaseEntity{

    @Column
    private String name;
    private UUID uuid;

    public Reservoir() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
