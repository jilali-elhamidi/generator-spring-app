package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.ProjectDto;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.mapper.ProjectMapper;
import com.example.modules.project_management.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService,
                                    ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<Project> entities = projectService.findAll();
        return ResponseEntity.ok(projectMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return projectService.findById(id)
                .map(projectMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(
            @Valid @RequestBody ProjectDto projectDto,
            UriComponentsBuilder uriBuilder) {

        Project entity = projectMapper.toEntity(projectDto);
        Project saved = projectService.save(entity);

        URI location = uriBuilder
                                .path("/api/projects/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(projectMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDto projectDto) {


        Project entityToUpdate = projectMapper.toEntity(projectDto);
        Project updatedEntity = projectService.update(id, entityToUpdate);

        return ResponseEntity.ok(projectMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}