package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Section;


public interface SectionRepository extends JpaRepository<Section,Integer> {

    boolean existsByNameAndCourseId(String name, Integer course_id);
}
