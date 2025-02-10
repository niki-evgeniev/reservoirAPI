package rest.reservoirapi.models.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ReservoirInformation {

    private String name;

    private UUID uuid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate addedDate;

    private double totalVolume;

    private double minimumFlowVolume;

    private double fillPercentage;

    private double availableVolume;

    private double volumePercentage;

    public ReservoirInformation() {
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

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getMinimumFlowVolume() {
        return minimumFlowVolume;
    }

    public void setMinimumFlowVolume(double minimumFlowVolume) {
        this.minimumFlowVolume = minimumFlowVolume;
    }

    public double getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(double fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    public double getAvailableVolume() {
        return availableVolume;
    }

    public void setAvailableVolume(double availableVolume) {
        this.availableVolume = availableVolume;
    }

    public double getVolumePercentage() {
        return volumePercentage;
    }

    public void setVolumePercentage(double volumePercentage) {
        this.volumePercentage = volumePercentage;
    }
}
