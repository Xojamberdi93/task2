package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
