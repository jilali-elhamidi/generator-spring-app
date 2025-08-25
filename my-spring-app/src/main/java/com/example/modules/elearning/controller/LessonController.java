package com.example.modules.elearning.controller;

import com.example.modules.elearning.dto.LessonDto;
import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.mapper.LessonMapper;
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
    private final LessonMapper lessonMapper;

    public LessonController(LessonService lessonService,
                                    LessonMapper lessonMapper) {
        this.lessonService = lessonService;
        this.lessonMapper = lessonMapper;
    }

    @GetMapping
    public ResponseEntity<List<LessonDto>> getAllLessons() {
        List<Lesson> entities = lessonService.findAll();
        return ResponseEntity.ok(lessonMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        return lessonService.findById(id)
                .map(lessonMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LessonDto> createLesson(
            @Valid @RequestBody LessonDto lessonDto,
            UriComponentsBuilder uriBuilder) {

        Lesson entity = lessonMapper.toEntity(lessonDto);
        Lesson saved = lessonService.save(entity);

        URI location = uriBuilder
                                .path("/api/lessons/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(lessonMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LessonDto>> createAllLessons(
            @Valid @RequestBody List<LessonDto> lessonDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Lesson> entities = lessonMapper.toEntityList(lessonDtoList);
        List<Lesson> savedEntities = lessonService.saveAll(entities);

        URI location = uriBuilder.path("/api/lessons").build().toUri();

        return ResponseEntity.created(location).body(lessonMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDto> updateLesson(
            @PathVariable Long id,
            @Valid @RequestBody LessonDto lessonDto) {


        Lesson entityToUpdate = lessonMapper.toEntity(lessonDto);
        Lesson updatedEntity = lessonService.update(id, entityToUpdate);

        return ResponseEntity.ok(lessonMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        boolean deleted = lessonService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}