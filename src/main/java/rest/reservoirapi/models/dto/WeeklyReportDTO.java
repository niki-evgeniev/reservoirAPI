package rest.reservoirapi.models.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class WeeklyReportDTO {

    private String reservoirName;

    private String totalVolume;

    private String usefulVolume;

    private LocalDate date;

    public WeeklyReportDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReservoirName() {
        return reservoirName;
    }

    public void setReservoirName(String reservoirName) {
        this.reservoirName = reservoirName;
    }

    public String getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(String totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getUsefulVolume() {
        return usefulVolume;
    }

    public void setUsefulVolume(String usefulVolume) {
        this.usefulVolume = usefulVolume;
    }
}
