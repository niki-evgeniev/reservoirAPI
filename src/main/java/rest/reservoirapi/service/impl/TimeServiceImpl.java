package rest.reservoirapi.service.impl;

import org.springframework.stereotype.Service;
import rest.reservoirapi.service.TimeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public String getDateNow() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatingDate = DateTimeFormatter.ofPattern("ddMMyyyy");
        String todayFormatedDate = today.format(formatingDate);
        return todayFormatedDate;
    }
}
