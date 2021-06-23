package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Section;
import uz.pdp.entity.Task;
import uz.pdp.payload.TaskDto;
import uz.pdp.repository.SectionRepository;
import uz.pdp.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    SectionRepository sectionRepository;

    @PostMapping
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto) {
        Optional<Section> optionalSection = sectionRepository.findById(taskDto.getSectionId());
        if (!optionalSection.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Section not found");
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setSection(optionalSection.get());
        taskRepository.save(task);
        return ResponseEntity.status(202).body("Task saved");
    }
    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(){
        List<Task> taskList = taskRepository.findAll();
        return ResponseEntity.ok(taskList);
    }
}
