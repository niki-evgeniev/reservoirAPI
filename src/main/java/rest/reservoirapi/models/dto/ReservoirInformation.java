package rest.reservoirapi.models.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    private double inflow_m3s;

    private double outflow_m3s;

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

    public double getInflow_m3s() {
        return inflow_m3s;
    }

    public void setInflow_m3s(double inflow_m3s) {
        this.inflow_m3s = inflow_m3s;
    }

    public double getOutflow_m3s() {
        return outflow_m3s;
    }

    public void setOutflow_m3s(double outflow_m3s) {
        this.outflow_m3s = outflow_m3s;
    }
}
