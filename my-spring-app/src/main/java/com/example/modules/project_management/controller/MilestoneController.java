package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.MilestoneDto;
import com.example.modules.project_management.model.Milestone;
import com.example.modules.project_management.mapper.MilestoneMapper;
import com.example.modules.project_management.service.MilestoneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;
    private final MilestoneMapper milestoneMapper;

    public MilestoneController(MilestoneService milestoneService,
                                    MilestoneMapper milestoneMapper) {
        this.milestoneService = milestoneService;
        this.milestoneMapper = milestoneMapper;
    }

    @GetMapping
    public ResponseEntity<List<MilestoneDto>> getAllMilestones() {
        List<Milestone> entities = milestoneService.findAll();
        return ResponseEntity.ok(milestoneMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MilestoneDto> getMilestoneById(@PathVariable Long id) {
        return milestoneService.findById(id)
                .map(milestoneMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MilestoneDto> createMilestone(
            @Valid @RequestBody MilestoneDto milestoneDto,
            UriComponentsBuilder uriBuilder) {

        Milestone entity = milestoneMapper.toEntity(milestoneDto);
        Milestone saved = milestoneService.save(entity);
        URI location = uriBuilder.path("/api/milestones/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(milestoneMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MilestoneDto> updateMilestone(
                @PathVariable Long id,
                @RequestBody MilestoneDto milestoneDto) {

                // Transformer le DTO en entity pour le service
                Milestone entityToUpdate = milestoneMapper.toEntity(milestoneDto);

                // Appel du service update
                Milestone updatedEntity = milestoneService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MilestoneDto updatedDto = milestoneMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
                    boolean deleted = milestoneService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}