package rest.reservoirapi.models.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Table
@Entity(name = "reservoir")
public class Reservoir extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "uuid", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;

    @Column(name = "added_date", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDate addedDate;

    @ManyToOne
    private WeeklyReport weeklyReport;

    //Общ обем
    @Column(name = "total_volume", nullable = false)
    private double totalVolume;

    //Мъртав обем
    @Column(name = "minimum_flowVolume", nullable = false)
    private double minimumFlowVolume;

    //Общ обем в %
    @Column(name = "fill_percentage", nullable = false)
    private double fillPercentage;

    //наличен полезен обем
    @Column(name = "available_volume", nullable = false)
    private double availableVolume;

    @Column(name = "inflow_m3s")
    private double inflow_m3s;

    @Column(name = "outflow_m3s")
    private double outflow_m3s;

    //Общ полезен обем в %
    @Column(name = "volume_percentage", nullable = false)
    private double volumePercentage;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    private SavedFiles savedFiles;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public SavedFiles getSavedFiles() {
        return savedFiles;
    }

    public void setSavedFiles(SavedFiles savedFiles) {
        this.savedFiles = savedFiles;
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

    public WeeklyReport getWeeklyReport() {
        return weeklyReport;
    }

    public void setWeeklyReport(WeeklyReport weeklyReport) {
        this.weeklyReport = weeklyReport;
    }
}




