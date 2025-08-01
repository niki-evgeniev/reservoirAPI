package rest.reservoirapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rest.reservoirapi.models.dto.ReservoirInformation;
import rest.reservoirapi.models.entity.Reservoir;
import rest.reservoirapi.models.entity.SavedFiles;
import rest.reservoirapi.repository.ReservoirRepository;
import rest.reservoirapi.repository.SavedFileRepository;
import rest.reservoirapi.service.InformationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InformationServiceImpl implements InformationService {

    private final ReservoirRepository reservoirRepository;
    private final ModelMapper modelMapper;
    private final SavedFileRepository savedFileRepository;

    public InformationServiceImpl(ReservoirRepository reservoirRepository, ModelMapper modelMapper,
                                  SavedFileRepository savedFileRepository) {
        this.reservoirRepository = reservoirRepository;
        this.modelMapper = modelMapper;
        this.savedFileRepository = savedFileRepository;
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

    @Override
    public List<ReservoirInformation> getLastInfo() {

        List<SavedFiles> lastSavedFile = savedFileRepository.findTopByOrderByIdDesc();
        increaseCounter(lastSavedFile);
        if (lastSavedFile.isEmpty()) {
            System.err.println("Last added file not exist");
            return List.of();
        }
        List<ReservoirInformation> infoReservoir = new ArrayList<>();
        for (SavedFiles savedFiles : lastSavedFile) {
            for (Reservoir reservoir : savedFiles.getReservoirList()) {
                infoReservoir.add(modelMapper.map(reservoir, ReservoirInformation.class));
            }
        }
        return infoReservoir;
    }

    @Override
    public ReservoirInformation getInformationForReservoir(String name) {
        Optional<Reservoir> reservoirInfo = reservoirRepository.findTopByNameOrderByIdDesc(name);
        if (reservoirInfo.isPresent()) {
            ReservoirInformation getInformation = modelMapper.map(reservoirInfo, ReservoirInformation.class);
            List<SavedFiles> lastSavedFile = savedFileRepository.findTopByOrderByIdDesc();
            increaseCounter(lastSavedFile);
            return getInformation;
        }
        return null;
    }

    private void increaseCounter(List<SavedFiles> lastSavedFile) {
        SavedFiles increaseCounter = lastSavedFile.getFirst();
        long currentCounter = increaseCounter.getCounter() + 1L;
        increaseCounter.setCounter(currentCounter);
        savedFileRepository.save(increaseCounter);
    }
}
