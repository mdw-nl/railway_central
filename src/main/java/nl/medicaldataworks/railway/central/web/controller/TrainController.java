package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Train;
import nl.medicaldataworks.railway.central.repository.TrainRepository;
import nl.medicaldataworks.railway.central.util.KeycloakUtil;
import nl.medicaldataworks.railway.central.util.PaginationUtil;
import nl.medicaldataworks.railway.central.util.ResponseUtil;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
public class TrainController {
    private final TrainRepository trainRepository;
    private KeycloakUtil keycloakUtil = new KeycloakUtil();
    public TrainController(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @GetMapping("/trains/{id}")
    public ResponseEntity<Train> getTrain(@PathVariable Long id, Authentication authentication) {
        log.debug("REST request to get train : {}", id);
        Optional<Train> train = trainRepository.findByIdAndOwnerName(id, keycloakUtil.getPreferredUsernameFromAuthentication(authentication));
        return ResponseUtil.wrapOrNotFound(train);
    }

    @GetMapping("/trains")
    public ResponseEntity<List<Train>> getAllTrains(Pageable pageable, Authentication authentication) {
        log.debug("REST request to get trains");
        Page<Train> page = trainRepository.findByOwnerName(pageable, keycloakUtil.getPreferredUsernameFromAuthentication(authentication));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/trains")
    public ResponseEntity<Train> createTrain(@RequestBody Train train, Authentication authentication) throws Exception {
        log.debug("REST request to save train : {}", train);
        if (train.getId() != null) {
            throw new Exception("A new train cannot already have an ID");
        }
        train.setOwnerName(keycloakUtil.getPreferredUsernameFromAuthentication(authentication));
        Train result = trainRepository.save(train);
        return ResponseEntity.created(new URI("/api/trains/" + result.getId()))
                .body(result);
    }

    @PutMapping("/trains")
    public ResponseEntity<Train> updateTrain(@RequestBody Train train, Authentication authentication) {
        log.debug("REST request to update train : {}", train);
        if (train.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        Optional<Train> oldTrain = trainRepository.findByIdAndOwnerName(train.getId(), train.getOwnerName());
        if(keycloakUtil.getPreferredUsernameFromAuthentication(authentication) != null && keycloakUtil.getPreferredUsernameFromAuthentication(authentication).equals(oldTrain.orElse(train).getOwnerName())){
            Train result = trainRepository.save(train);
            return ResponseEntity.ok()
                    .body(result);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/trains/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id, Authentication authentication) {
        log.debug("REST request to delete train : {}", id);
        Optional<Train> train = trainRepository.findById(id);
        if(keycloakUtil.getPreferredUsernameFromAuthentication(authentication) != null && train.isPresent() &&
                keycloakUtil.getPreferredUsernameFromAuthentication(authentication).equals(train.get().getOwnerName())){
            trainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
