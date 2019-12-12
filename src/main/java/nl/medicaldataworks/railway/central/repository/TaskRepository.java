package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
