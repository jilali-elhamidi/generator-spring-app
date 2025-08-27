package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.MilestoneDto;
import com.example.modules.project_management.dtosimple.MilestoneSimpleDto;
import com.example.modules.project_management.model.Milestone;
import com.example.modules.project_management.mapper.MilestoneMapper;
import com.example.modules.project_management.service.MilestoneService;
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
 * Controller for managing Milestone entities.
 */
@RestController
@RequestMapping("/api/milestones")
public class MilestoneController extends BaseController<Milestone, MilestoneDto, MilestoneSimpleDto> {

    public MilestoneController(MilestoneService milestoneService,
                                    MilestoneMapper milestoneMapper) {
        super(milestoneService, milestoneMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MilestoneDto>> getAllMilestones(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MilestoneDto>> searchMilestones(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Milestone.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MilestoneDto> getMilestoneById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MilestoneDto> createMilestone(
            @Valid @RequestBody MilestoneDto milestoneDto,
            UriComponentsBuilder uriBuilder) {

        Milestone entity = mapper.toEntity(milestoneDto);
        Milestone saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/milestones/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MilestoneDto>> createAllMilestones(
            @Valid @RequestBody List<MilestoneDto> milestoneDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Milestone> entities = mapper.toEntityList(milestoneDtoList);
        List<Milestone> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/milestones").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MilestoneDto> updateMilestone(
            @PathVariable Long id,
            @Valid @RequestBody MilestoneDto milestoneDto) {

        Milestone entityToUpdate = mapper.toEntity(milestoneDto);
        Milestone updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        return doDelete(id);
    }
}