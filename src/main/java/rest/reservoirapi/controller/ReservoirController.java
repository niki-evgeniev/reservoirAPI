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
        LOGGER.warn("GET Request from {}", ipAddress);
        System.out.println("Sending request");
        return ResponseEntity.ok(info);
    }


    @GetMapping("/info/{res}")
    ResponseEntity<?> getReservoirInfo(@PathVariable("res") String res) {
        ReservoirInformation reservoirInformation = informationService.getInformationForReservoir(res);
        return ResponseEntity.ok(reservoirInformation);
    }
}
