package rest.reservoirapi.service;

import rest.reservoirapi.models.dto.ReservoirInformation;

import java.util.List;

public interface InformationService {

    List<ReservoirInformation> getInformation();
}
