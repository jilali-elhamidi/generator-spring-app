package com.example.modules.elearning.controller;

import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

private final InstructorService instructorService;

public InstructorController(InstructorService instructorService) {
this.instructorService = instructorService;
}

@GetMapping
public ResponseEntity<List<Instructor>> getAllInstructors() {
return ResponseEntity.ok(instructorService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id) {
return instructorService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<Instructor> createInstructor(
@Valid @RequestBody Instructor instructor,
UriComponentsBuilder uriBuilder) {

Instructor saved = instructorService.save(instructor);
URI location = uriBuilder.path("/api/instructors/{id}")
.buildAndExpand(saved.getId()).toUri();

return ResponseEntity.created(location).body(saved);
}

@PutMapping("/{id}")
public ResponseEntity<Instructor> updateInstructor(
@PathVariable Long id,
@Valid @RequestBody Instructor instructorRequest) {

try {
Instructor updated = instructorService.update(id, instructorRequest);
return ResponseEntity.ok(updated);
} catch (RuntimeException e) {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
    instructorService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
