package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndOwnerName(Long id, String ownerName);

    List<Task> findByTrainIdAndOwnerName(Long id, String name);

    Page<Task> findByStationId(Pageable pageable, Long id);
}