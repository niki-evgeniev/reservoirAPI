package rest.reservoirapi.models;

import java.time.LocalDate;

public interface WeeklySnapshotRow {

    String getName();
    LocalDate getWeekDate();          // alias week_date
    Double getVolumePercentage();     // alias volume_percentage
    Double getFillPercentage();       // alias fill_percentage
}
