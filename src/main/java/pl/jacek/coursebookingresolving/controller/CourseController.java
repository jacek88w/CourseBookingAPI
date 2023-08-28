package pl.jacek.coursebookingresolving.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jacek.coursebookingresolving.entity.Course;
import pl.jacek.coursebookingresolving.dto.CourseDTO;
import pl.jacek.coursebookingresolving.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> findAll() {
        return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.create(courseDTO),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody CourseDTO courseDTO, @PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.update(courseDTO, id),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.deleteById(id),
                HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll() {
        return new ResponseEntity<>(courseService.deleteAll(),
                HttpStatus.OK);
    }
}
