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
        URI location = uriBuilder.path("/api/subtasks/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(subtaskMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<SubtaskDto> updateSubtask(
                @PathVariable Long id,
                @RequestBody SubtaskDto subtaskDto) {

                // Transformer le DTO en entity pour le service
                Subtask entityToUpdate = subtaskMapper.toEntity(subtaskDto);

                // Appel du service update
                Subtask updatedEntity = subtaskService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                SubtaskDto updatedDto = subtaskMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteSubtask(@PathVariable Long id) {
                    boolean deleted = subtaskService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}