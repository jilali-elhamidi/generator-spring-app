package com.example.modules.elearning.controller;

import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {
return ResponseEntity.ok(instructorService.save(instructor));
}

@PutMapping("/{id}")
public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor) {
return ResponseEntity.ok(instructorService.save(instructor));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
    instructorService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
