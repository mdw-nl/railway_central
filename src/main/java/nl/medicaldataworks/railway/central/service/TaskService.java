package nl.medicaldataworks.railway.central.service;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.domain.Station;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import nl.medicaldataworks.railway.central.repository.StationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TaskService {
    private TaskRepository taskRepository;
    private StationRepository stationRepository;

    public TaskService(TaskRepository taskRepository, StationRepository stationRepository) {
        this.taskRepository = taskRepository;
        this.stationRepository = stationRepository;
    }

    public Optional<Task> findTask(Long id){
        return taskRepository.findById(id);
    }

    public Page<Task> findTasks(Pageable pageable, CalculationStatus calculationStatus, Long stationId){
        Page<Task> page;
        if(stationId != null && calculationStatus != null){
            page = taskRepository.findByStationIdAndCalculationStatus(pageable, stationId, calculationStatus);
        } else if(stationId != null){
            page = taskRepository.findByStationId(pageable, stationId);
        } else if(calculationStatus != null) {
            page = taskRepository.findByCalculationStatus(pageable, calculationStatus);
        } else {
            page = taskRepository.findAll(pageable);
        }
        return page;
    }

    //TODO: BAH VIES DOE ANDERS
    public Long getStationIdForStationName(String stationName){
        Optional<Station> station =stationRepository.findByName(stationName);
        if (station.isPresent()){
            return station.get().getId();
        } else {
            return null;
        }
    }
}
