package rest.reservoirapi.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.reservoirapi.models.dto.ReservoirInformation;
import rest.reservoirapi.service.InformationService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservoirController {

    private final InformationService informationService;

    public ReservoirController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/info")
    ResponseEntity<?> getInfoReservoir(){
        List<ReservoirInformation> info2 = informationService.getLastInfo();
        return ResponseEntity.ok(info2);
    }
}
