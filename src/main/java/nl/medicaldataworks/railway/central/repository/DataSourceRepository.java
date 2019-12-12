package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {
}
