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

        URI location = uriBuilder
                                .path("/api/milestones/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(milestoneMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MilestoneDto>> createAllMilestones(
            @Valid @RequestBody List<MilestoneDto> milestoneDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Milestone> entities = milestoneMapper.toEntityList(milestoneDtoList);
        List<Milestone> savedEntities = milestoneService.saveAll(entities);

        URI location = uriBuilder.path("/api/milestones").build().toUri();

        return ResponseEntity.created(location).body(milestoneMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MilestoneDto> updateMilestone(
            @PathVariable Long id,
            @Valid @RequestBody MilestoneDto milestoneDto) {


        Milestone entityToUpdate = milestoneMapper.toEntity(milestoneDto);
        Milestone updatedEntity = milestoneService.update(id, entityToUpdate);

        return ResponseEntity.ok(milestoneMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        boolean deleted = milestoneService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}