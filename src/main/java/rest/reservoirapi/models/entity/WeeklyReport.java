package rest.reservoirapi.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "weekly_report")
public class WeeklyReport extends BaseEntity {


    @Column(name = "reservoir_name", nullable = false)
    private String reservoirName;

    @Column(name = "total_volume", nullable = false)
    private String totalVolume;

    @Column(name = "useful_volume", nullable = false)
    private String usefulVolume;

    @Column(name = "date", columnDefinition = "DATETIME(0)")

    private LocalDate date;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDate createAt;

    public WeeklyReport() {
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

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }
}
