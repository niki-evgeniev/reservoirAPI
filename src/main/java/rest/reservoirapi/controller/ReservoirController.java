package rest.reservoirapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.reservoirapi.models.dto.ReservoirInformation;
import rest.reservoirapi.service.InformationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservoirController {

    private final InformationService informationService;
    private final Logger LOGGER = LoggerFactory.getLogger(ReservoirController.class);

    public ReservoirController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/info")
    ResponseEntity<?> getInfoReservoir(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        List<ReservoirInformation> info = informationService.getLastInfo();
        String formattedDate = getString();
        System.out.println("Sending request from RESERVOIR DETAILS " + formattedDate);
        return ResponseEntity.ok(info);
    }

    @GetMapping("/info/{name}")
    ResponseEntity<?> getReservoirInfo(@PathVariable("name") String name) {
        ReservoirInformation reservoirInformation = informationService.getInformationForReservoir(name);
        String formattedDate = getString();
        System.out.println("Sending request from RESERVOIR DETAIL = " + name + " " + formattedDate);
        return ResponseEntity.ok(reservoirInformation);
    }

    private static String getString() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formattedDate = time.format(formatter);
        return formattedDate;
    }
}
