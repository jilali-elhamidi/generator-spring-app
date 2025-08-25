package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.SubtaskDto;
import com.example.modules.project_management.model.Subtask;
import com.example.modules.project_management.mapper.SubtaskMapper;
import com.example.modules.project_management.service.SubtaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final SubtaskMapper subtaskMapper;

    public SubtaskController(SubtaskService subtaskService,
                                    SubtaskMapper subtaskMapper) {
        this.subtaskService = subtaskService;
        this.subtaskMapper = subtaskMapper;
    }

    @GetMapping
    public ResponseEntity<List<SubtaskDto>> getAllSubtasks() {
        List<Subtask> entities = subtaskService.findAll();
        return ResponseEntity.ok(subtaskMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubtaskDto> getSubtaskById(@PathVariable Long id) {
        return subtaskService.findById(id)
                .map(subtaskMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubtaskDto> createSubtask(
            @Valid @RequestBody SubtaskDto subtaskDto,
            UriComponentsBuilder uriBuilder) {

        Subtask entity = subtaskMapper.toEntity(subtaskDto);
        Subtask saved = subtaskService.save(entity);

        URI location = uriBuilder
                                .path("/api/subtasks/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(subtaskMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SubtaskDto>> createAllSubtasks(
            @Valid @RequestBody List<SubtaskDto> subtaskDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Subtask> entities = subtaskMapper.toEntityList(subtaskDtoList);
        List<Subtask> savedEntities = subtaskService.saveAll(entities);

        URI location = uriBuilder.path("/api/subtasks").build().toUri();

        return ResponseEntity.created(location).body(subtaskMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubtaskDto> updateSubtask(
            @PathVariable Long id,
            @Valid @RequestBody SubtaskDto subtaskDto) {


        Subtask entityToUpdate = subtaskMapper.toEntity(subtaskDto);
        Subtask updatedEntity = subtaskService.update(id, entityToUpdate);

        return ResponseEntity.ok(subtaskMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Long id) {
        boolean deleted = subtaskService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}