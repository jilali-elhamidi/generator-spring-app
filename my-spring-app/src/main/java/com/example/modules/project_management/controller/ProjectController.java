package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.ProjectDto;
import com.example.modules.project_management.dtosimple.ProjectSimpleDto;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.mapper.ProjectMapper;
import com.example.modules.project_management.service.ProjectService;
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
 * Controller for managing Project entities.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController extends BaseController<Project, ProjectDto, ProjectSimpleDto> {

    public ProjectController(ProjectService projectService,
                                    ProjectMapper projectMapper) {
        super(projectService, projectMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDto>> getAllProjects(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProjectDto>> searchProjects(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Project.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(
            @Valid @RequestBody ProjectDto projectDto,
            UriComponentsBuilder uriBuilder) {

        Project entity = mapper.toEntity(projectDto);
        Project saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/projects/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProjectDto>> createAllProjects(
            @Valid @RequestBody List<ProjectDto> projectDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Project> entities = mapper.toEntityList(projectDtoList);
        List<Project> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/projects").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDto projectDto) {

        Project entityToUpdate = mapper.toEntity(projectDto);
        Project updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        return doDelete(id);
    }
}