package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    boolean existsByName(String name);
}
