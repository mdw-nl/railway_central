package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Station;
import nl.medicaldataworks.railway.central.repository.StationRepository;
import nl.medicaldataworks.railway.central.util.PaginationUtil;
import nl.medicaldataworks.railway.central.util.ResponseUtil;
import nl.medicaldataworks.railway.central.web.dto.StationDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
public class StationController {
    private final StationRepository stationRepository;
    private ModelMapper modelMapper;
    public StationController(StationRepository stationRepository, ModelMapper modelMapper) {
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/stations/{id}")
    public ResponseEntity<Station> getStation(@PathVariable Long id) {
        log.debug("REST request to get station : {}", id);
        Optional<Station> station = stationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(station);
    }

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getAllStations(Pageable pageable) {
        log.debug("REST request to get stations");
        Page<Station> page = stationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/stations")
    public ResponseEntity<Station> createStation(@RequestBody StationDto stationDto) throws Exception {
        log.debug("REST request to save station : {}", stationDto);
        if (stationDto.getId() != null) {
            throw new Exception("A new station cannot already have an ID");
        }
        Station station = modelMapper.map(stationDto, Station.class);
        Station result = stationRepository.save(station);
        return ResponseEntity.created(new URI("/api/stations/" + result.getId()))
                .body(result);
    }

    @PutMapping("/stations")
    public ResponseEntity<Station> updateStation(@RequestBody Station station) {
        log.debug("REST request to update station : {}", station);
        if (station.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        Station result = stationRepository.save(station);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/stations/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        log.debug("REST request to delete station : {}", id);
        Optional<Station> station = stationRepository.findById(id);
        stationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
