package rest.reservoirapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rest.reservoirapi.models.dto.ReservoirInformation;
import rest.reservoirapi.models.entity.Reservoir;
import rest.reservoirapi.repository.ReservoirRepository;
import rest.reservoirapi.service.InformationService;

import java.util.ArrayList;
import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {

    private final ReservoirRepository reservoirRepository;
    private final ModelMapper modelMapper;

    public InformationServiceImpl(ReservoirRepository reservoirRepository, ModelMapper modelMapper) {
        this.reservoirRepository = reservoirRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReservoirInformation> getInformation() {
        List<Reservoir> all = reservoirRepository.findAll();
        List<ReservoirInformation> info = new ArrayList<>();

        for (Reservoir reservoir : all) {
            info.add(modelMapper.map(reservoir, ReservoirInformation.class));
        }

        return info;
    }
}
