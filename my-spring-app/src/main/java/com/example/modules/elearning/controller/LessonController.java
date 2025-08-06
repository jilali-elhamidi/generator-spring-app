package com.example.modules.elearning.controller;

import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
public ResponseEntity<Lesson> createLesson(
@Valid @RequestBody Lesson lesson,
UriComponentsBuilder uriBuilder) {

Lesson saved = lessonService.save(lesson);
URI location = uriBuilder.path("/api/lessons/{id}")
.buildAndExpand(saved.getId()).toUri();

return ResponseEntity.created(location).body(saved);
}

@PutMapping("/{id}")
public ResponseEntity<Lesson> updateLesson(
@PathVariable Long id,
@Valid @RequestBody Lesson lessonRequest) {

try {
Lesson updated = lessonService.update(id, lessonRequest);
return ResponseEntity.ok(updated);
} catch (RuntimeException e) {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
    lessonService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
