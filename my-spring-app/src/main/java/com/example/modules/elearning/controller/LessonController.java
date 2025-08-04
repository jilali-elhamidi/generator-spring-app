package com.example.modules.elearning.controller;

import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

private final LessonService lessonService;

public LessonController(LessonService lessonService) {
this.lessonService = lessonService;
}

@GetMapping
public ResponseEntity<List<Lesson>> getAllLessons() {
return ResponseEntity.ok(lessonService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
return lessonService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
return ResponseEntity.ok(lessonService.save(lesson));
}

@PutMapping("/{id}")
public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
return ResponseEntity.ok(lessonService.save(lesson));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
    lessonService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
