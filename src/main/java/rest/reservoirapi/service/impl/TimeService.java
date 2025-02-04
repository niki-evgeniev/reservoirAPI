package rest.reservoirapi.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TimeService {

    public String getDateNow (){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatingDate = DateTimeFormatter.ofPattern("ddMMyyyy");
        String todayFormatedDate = today.format(formatingDate);
        System.out.println(todayFormatedDate);
        return todayFormatedDate;
    }
}
