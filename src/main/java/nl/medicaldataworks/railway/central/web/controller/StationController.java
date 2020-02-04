package nl.medicaldataworks.railway.central.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Station;
import nl.medicaldataworks.railway.central.repository.StationRepository;
import nl.medicaldataworks.railway.central.service.StationService;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
@Api(tags = { "station-controller" })
public class StationController {
    private final StationRepository stationRepository;
    private final StationService stationService;
    private ModelMapper modelMapper;

    public StationController(StationRepository stationRepository, StationService stationService, ModelMapper modelMapper) {
        this.stationRepository = stationRepository;
        this.stationService = stationService;
        this.modelMapper = modelMapper;
    }


    @ApiOperation(value = "Find a station by ID.")
    @GetMapping("/stations/{id}")
    public ResponseEntity<Station> getStation(@ApiParam(value = "ID of the station to return.", required = true)  @PathVariable Long id) {
        log.debug("REST request to get station : {}", id);
        Optional<Station> station = stationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(station);
    }

    @GetMapping("/stations")
    @ApiOperation(value = "Search stations based on query parameters.")
    public ResponseEntity<List<Station>> getAllStations(@ApiIgnore("Ignored because swagger ui shows the wrong params.") Pageable pageable,
                                                        @ApiParam(value = "Filter tasks on station name.")
                                                        @RequestParam(value = "station-name") Optional<String> stationName) {
        log.debug("REST request to get stations");
        if(stationName.isPresent()){
            Optional<Long> stationId = stationService.getStationIdForStationName(stationName);
            if(!stationId.isPresent()){
                return ResponseEntity.ok(Collections.emptyList());
            }
            Optional<Station> station = stationRepository.findById(stationId.get());
            return ResponseEntity.ok(Collections.singletonList(station.get()));
        } else {
            Page<Station> page = stationRepository.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    @PostMapping("/stations")
    @ApiOperation(value = "Add a new station. Needed before starting a station locally the first time.")
    public ResponseEntity<Station> createStation(@ApiParam(value = "Station object to add.", required = true) @RequestBody StationDto stationDto) throws Exception {
        log.debug("REST request to save station : {}", stationDto);
        if (stationDto.getId() != null) {
            throw new IllegalArgumentException("A new station cannot already have an ID");
        }
        Station station = modelMapper.map(stationDto, Station.class);
        Station result = stationRepository.save(station);
        return ResponseEntity.created(new URI("/api/stations/" + result.getId()))
                .body(result);
    }

    @PutMapping("/stations")
    @ApiOperation(value = "Update an existing station.")
    public ResponseEntity<Station> updateStation(@ApiParam(value = "New station object to replace the existing one. Should have the same ID as the old station.", required = true) @RequestBody Station station) {
        log.debug("REST request to update station : {}", station);
        if (station.getId() == null) {
            throw new IllegalArgumentException("Invalid ID");
        }
        Station result = stationRepository.save(station);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/stations/{id}")
    @ApiOperation(value = "Delete a station by ID.")
    public ResponseEntity<Void> deleteStation(@ApiParam(value = "ID of the station to delete.") @PathVariable Long id) {
        log.debug("REST request to delete station : {}", id);
        Optional<Station> station = stationRepository.findById(id);
        stationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
