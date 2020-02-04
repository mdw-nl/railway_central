package nl.medicaldataworks.railway.central.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Train;
import nl.medicaldataworks.railway.central.repository.TrainRepository;
import nl.medicaldataworks.railway.central.util.PaginationUtil;
import nl.medicaldataworks.railway.central.util.ResponseUtil;
import nl.medicaldataworks.railway.central.web.dto.TrainDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
@Api(tags = { "train-controller" })
public class TrainController {
    private final TrainRepository trainRepository;
    private ModelMapper modelMapper;

    public TrainController(TrainRepository trainRepository, ModelMapper modelMapper) {
        this.trainRepository = trainRepository;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value="Find a train by ID.")
    @GetMapping("/trains/{id}")
    public ResponseEntity<Train> getTrain(@ApiParam(value = "ID of the station to return.")
                                          @PathVariable Long id) {
        log.debug("REST request to get train : {}", id);
        Optional<Train> train = trainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(train);
    }

    @ApiOperation(value="Returns all available trains.")
    @GetMapping("/trains")
    public ResponseEntity<List<Train>> getAllTrains(@ApiIgnore("Ignored because swagger ui shows the wrong params.") Pageable pageable) {
        log.debug("REST request to get trains");
        Page<Train> page = trainRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @ApiOperation(value="Add a new train.")
    @PostMapping("/trains")
    public ResponseEntity<Train> createTrain(@ApiParam(value = "Train object to add.")
                                             @RequestBody TrainDto trainDto, Authentication authentication) throws IllegalArgumentException, URISyntaxException {
        log.debug("REST request to save train : {}", trainDto);
        if (trainDto.getId() != null) {
            throw new IllegalArgumentException("A new train cannot already have an ID");
        }
        Train train = modelMapper.map(trainDto, Train.class);
        train.setOwnerId(authentication.getName());
        Train result = trainRepository.save(train);
        return ResponseEntity.created(new URI("/api/trains/" + result.getId()))
                .body(result);
    }

    @ApiOperation(value="Update an existing train.")
    @PutMapping("/trains")
    public ResponseEntity<Train> updateTrain(@ApiParam(value = "New train object to replace the existing one. Should have the same ID as the old station.", required = true)
                                             @RequestBody TrainDto trainDto, Authentication authentication) throws IllegalArgumentException {
        log.debug("REST request to update train : {}", trainDto);
        if (trainDto.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        Optional<Train> train = trainRepository.findById(trainDto.getId());
        Train validTrain = train.orElseThrow(() -> new IllegalArgumentException("No valid train for supplied ID."));
        validTrain.setDockerImageUrl(trainDto.getDockerImageUrl());
        validTrain.setCalculationStatus(trainDto.getCalculationStatus());
        validTrain.setName(trainDto.getName());
        Train result = trainRepository.save(validTrain);
        return ResponseEntity.ok()
                .body(result);

    }

    @ApiOperation(value="Delete an existing train.")
    @DeleteMapping("/trains/{id}")
    public ResponseEntity<Void> deleteTrain(@ApiParam(value = "ID of the station to delete.", required = true)
                                            @PathVariable Long id, Authentication authentication) throws IllegalArgumentException {
        log.debug("REST request to delete train : {}", id);
        Optional<Train> train = trainRepository.findById(id);
        Train validTrain = train.orElseThrow(() -> new IllegalArgumentException("No valid train for supplied ID."));
        if(!validTrain.getOwnerId().equals(authentication.getName())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        trainRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
