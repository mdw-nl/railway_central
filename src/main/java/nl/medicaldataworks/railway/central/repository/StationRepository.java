package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

}
