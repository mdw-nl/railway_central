package nl.medicaldataworks.railway.central.service;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Station;
import nl.medicaldataworks.railway.central.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class StationService {
    private StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Long getStationIdForStationName(String stationName){
        Optional<Station> station =stationRepository.findByName(stationName);
        if (station.isPresent()){
            return station.get().getId();
        } else {
            return null;
        }
    }
}
