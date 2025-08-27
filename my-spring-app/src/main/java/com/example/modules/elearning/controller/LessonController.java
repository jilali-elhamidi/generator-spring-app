package com.example.modules.elearning.controller;

import com.example.modules.elearning.dto.LessonDto;
import com.example.modules.elearning.dtosimple.LessonSimpleDto;
import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.mapper.LessonMapper;
import com.example.modules.elearning.service.LessonService;
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
 * Controller for managing Lesson entities.
 */
@RestController
@RequestMapping("/api/lessons")
public class LessonController extends BaseController<Lesson, LessonDto, LessonSimpleDto> {

    public LessonController(LessonService lessonService,
                                    LessonMapper lessonMapper) {
        super(lessonService, lessonMapper);
    }

    @GetMapping
    public ResponseEntity<Page<LessonDto>> getAllLessons(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<LessonDto>> searchLessons(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Lesson.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<LessonDto> createLesson(
            @Valid @RequestBody LessonDto lessonDto,
            UriComponentsBuilder uriBuilder) {

        Lesson entity = mapper.toEntity(lessonDto);
        Lesson saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/lessons/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LessonDto>> createAllLessons(
            @Valid @RequestBody List<LessonDto> lessonDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Lesson> entities = mapper.toEntityList(lessonDtoList);
        List<Lesson> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/lessons").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDto> updateLesson(
            @PathVariable Long id,
            @Valid @RequestBody LessonDto lessonDto) {

        Lesson entityToUpdate = mapper.toEntity(lessonDto);
        Lesson updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        return doDelete(id);
    }
}