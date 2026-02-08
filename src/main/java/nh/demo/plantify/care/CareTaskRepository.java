package nh.demo.plantify.care;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


interface CareTaskRepository extends Repository<CareTask, UUID> {

    CareTask save(CareTask task);

    List<CareTask> saveAll(Iterable<CareTask> tasks);

    Optional<CareTask> findById(UUID id);

    List<CareTask> findAll();

    List<CareTask> findAllByOrderByNextDueDate();
}
