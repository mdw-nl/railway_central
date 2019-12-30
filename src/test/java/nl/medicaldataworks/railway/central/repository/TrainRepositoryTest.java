package nl.medicaldataworks.railway.central.repository;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Train;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import static nl.medicaldataworks.railway.central.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DataJpaTest
public class TrainRepositoryTest {

    @Autowired
    private TrainRepository trainRepository;

    @Test
    public void findByOwnerId(){
        Page<Train> trains = trainRepository.findByOwnerId(PageRequest.of(PAGE, PAGE_SIZE), OWNER_ID);
        assertEquals(1, trains.getTotalElements());
        for(Train train : trains){
            assertEquals(OWNER_ID, train.getOwnerId());
        }
    }
}
