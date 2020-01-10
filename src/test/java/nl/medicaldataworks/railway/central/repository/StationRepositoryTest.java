package nl.medicaldataworks.railway.central.repository;


import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.IntegrationTest;
import nl.medicaldataworks.railway.central.domain.Station;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static nl.medicaldataworks.railway.central.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DataJpaTest
@Category(IntegrationTest.class)
public class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    public void findByStationIdAndCalculationStatus() {
        Pageable pageable = PageRequest.of(PAGE, PAGE_SIZE);
        Page<Station> stations = stationRepository.findAll(pageable);
        assertEquals(NUMBER_OF_STATIONS, stations.getTotalElements());
    }
}
