package rest.reservoirapi.models.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "saved_file")
public class SavedFiles extends BaseEntity {

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "added_date", nullable = false)
    private LocalDate addedDate;

    @Column(name = "is_saved")
    private boolean isSaved = false;

    @OneToMany(mappedBy = "savedFiles", fetch = FetchType.EAGER)
    List<Reservoir> reservoirList;

    public SavedFiles() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public List<Reservoir> getReservoirList() {
        return reservoirList;
    }

    public void setReservoirList(List<Reservoir> reservoirList) {
        this.reservoirList = reservoirList;
    }
}
