package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Course;
import uz.pdp.payload.CourseDto;
import uz.pdp.repository.CourseRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses(){
        List<Course> courseList = courseRepository.findAll();
        return ResponseEntity.ok(courseList);
    }
    @PostMapping
    public ResponseEntity<?> addCourse(@Valid @RequestBody  CourseDto courseDto){
        boolean exists = courseRepository.existsByName(courseDto.getName());
        if (exists)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Such a course aleady exist");
        Course course = new Course();
        course.setName(courseDto.getName());
        courseRepository.save(course);
        return ResponseEntity.status(200).body("Course added");
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCourse(@Valid @PathVariable Integer id,@RequestBody CourseDto courseDto){
         Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        boolean exists = courseRepository.existsByName(courseDto.getName());
        if (exists)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Such a course aleady exist");
        Course course = optionalCourse.get();
        course.setName(courseDto.getName());
        courseRepository.save(course);
        return ResponseEntity.status(203).body("Course edited");

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id){
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        courseRepository.deleteById(id);
        return ResponseEntity.ok("Course deleted");
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
