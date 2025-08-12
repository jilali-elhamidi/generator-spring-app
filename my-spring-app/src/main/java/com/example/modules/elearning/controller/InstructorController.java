package com.example.modules.elearning.controller;

import com.example.modules.elearning.dto.InstructorDto;
import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.mapper.InstructorMapper;
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
    private final InstructorMapper instructorMapper;

    public InstructorController(InstructorService instructorService,
                                    InstructorMapper instructorMapper) {
        this.instructorService = instructorService;
        this.instructorMapper = instructorMapper;
    }

    @GetMapping
    public ResponseEntity<List<InstructorDto>> getAllInstructors() {
        List<Instructor> entities = instructorService.findAll();
        return ResponseEntity.ok(instructorMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDto> getInstructorById(@PathVariable Long id) {
        return instructorService.findById(id)
                .map(instructorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InstructorDto> createInstructor(
            @Valid @RequestBody InstructorDto instructorDto,
            UriComponentsBuilder uriBuilder) {

        Instructor entity = instructorMapper.toEntity(instructorDto);
        Instructor saved = instructorService.save(entity);
        URI location = uriBuilder.path("/api/instructors/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(instructorMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDto> updateInstructor(
            @PathVariable Long id,
            @Valid @RequestBody InstructorDto instructorDto) {

        try {
            Instructor updatedEntity = instructorService.update(
                    id,
                    instructorMapper.toEntity(instructorDto)
            );
            return ResponseEntity.ok(instructorMapper.toDto(updatedEntity));
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