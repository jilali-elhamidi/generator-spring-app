package com.example.modules.elearning.controller;

import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

private final CourseService courseService;

public CourseController(CourseService courseService) {
this.courseService = courseService;
}

@GetMapping
public ResponseEntity<List<Course>> getAllCourses() {
return ResponseEntity.ok(courseService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
return courseService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<Course> createCourse(@RequestBody Course course) {
return ResponseEntity.ok(courseService.save(course));
}

@PutMapping("/{id}")
public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
return ResponseEntity.ok(courseService.save(course));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
    courseService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
