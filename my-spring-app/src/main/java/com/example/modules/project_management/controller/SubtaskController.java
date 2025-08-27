package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.SubtaskDto;
import com.example.modules.project_management.dtosimple.SubtaskSimpleDto;
import com.example.modules.project_management.model.Subtask;
import com.example.modules.project_management.mapper.SubtaskMapper;
import com.example.modules.project_management.service.SubtaskService;
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
 * Controller for managing Subtask entities.
 */
@RestController
@RequestMapping("/api/subtasks")
public class SubtaskController extends BaseController<Subtask, SubtaskDto, SubtaskSimpleDto> {

    public SubtaskController(SubtaskService subtaskService,
                                    SubtaskMapper subtaskMapper) {
        super(subtaskService, subtaskMapper);
    }

    @GetMapping
    public ResponseEntity<Page<SubtaskDto>> getAllSubtasks(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SubtaskDto>> searchSubtasks(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Subtask.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubtaskDto> getSubtaskById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<SubtaskDto> createSubtask(
            @Valid @RequestBody SubtaskDto subtaskDto,
            UriComponentsBuilder uriBuilder) {

        Subtask entity = mapper.toEntity(subtaskDto);
        Subtask saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/subtasks/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SubtaskDto>> createAllSubtasks(
            @Valid @RequestBody List<SubtaskDto> subtaskDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Subtask> entities = mapper.toEntityList(subtaskDtoList);
        List<Subtask> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/subtasks").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubtaskDto> updateSubtask(
            @PathVariable Long id,
            @Valid @RequestBody SubtaskDto subtaskDto) {

        Subtask entityToUpdate = mapper.toEntity(subtaskDto);
        Subtask updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Long id) {
        return doDelete(id);
    }
}