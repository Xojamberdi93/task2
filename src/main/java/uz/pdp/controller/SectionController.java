package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Course;
import uz.pdp.entity.Section;
import uz.pdp.payload.SectionDto;
import uz.pdp.repository.CourseRepository;
import uz.pdp.repository.SectionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SectionController {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<?> getAllSections() {
        List<Section> sectionList = sectionRepository.findAll();
        return ResponseEntity.ok(sectionList);
    }

    @PostMapping
    public HttpEntity<?> addSection(@RequestBody SectionDto sectionDto) {
        boolean exists = sectionRepository.existsByNameAndCourseId(sectionDto.getName(), sectionDto.getCourseId());
        if (exists)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This section already exist");
        Optional<Course> optionalCourse = courseRepository.findById(sectionDto.getCourseId());
        if(!optionalCourse.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        Section section = new Section();
        section.setName(sectionDto.getName());
        section.setDescription(sectionDto.getDescription());
        section.setCourse(optionalCourse.get());
        sectionRepository.save(section);
        return ResponseEntity.status(200).body("Section saved");
    }
}
