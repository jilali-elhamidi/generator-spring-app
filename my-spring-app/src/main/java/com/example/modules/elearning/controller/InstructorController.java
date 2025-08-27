package com.example.modules.elearning.controller;

import com.example.modules.elearning.dto.InstructorDto;
import com.example.modules.elearning.dtosimple.InstructorSimpleDto;
import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.mapper.InstructorMapper;
import com.example.modules.elearning.service.InstructorService;
import com.example.core.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing Instructor entities.
 */
@RestController
@RequestMapping("/api/instructors")
public class InstructorController extends BaseController<Instructor, InstructorDto, InstructorSimpleDto> {

    public InstructorController(InstructorService instructorService,
                                    InstructorMapper instructorMapper) {
        super(instructorService, instructorMapper);
    }

    @GetMapping
    public ResponseEntity<Page<InstructorDto>> getAllInstructors(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InstructorDto>> searchInstructors(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Instructor.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDto> getInstructorById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<InstructorDto> createInstructor(
            @Valid @RequestBody InstructorDto instructorDto,
            UriComponentsBuilder uriBuilder) {

        Instructor entity = mapper.toEntity(instructorDto);
        Instructor saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/instructors/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<InstructorDto>> createAllInstructors(
            @Valid @RequestBody List<InstructorDto> instructorDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Instructor> entities = mapper.toEntityList(instructorDtoList);
        List<Instructor> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/instructors").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDto> updateInstructor(
            @PathVariable Long id,
            @Valid @RequestBody InstructorDto instructorDto) {

        Instructor entityToUpdate = mapper.toEntity(instructorDto);
        Instructor updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        return doDelete(id);
    }
}