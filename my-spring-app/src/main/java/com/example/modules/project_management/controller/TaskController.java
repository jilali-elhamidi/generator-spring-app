package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TaskDto;
import com.example.modules.project_management.dtosimple.TaskSimpleDto;
import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.mapper.TaskMapper;
import com.example.modules.project_management.service.TaskService;
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
 * Controller for managing Task entities.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController extends BaseController<Task, TaskDto, TaskSimpleDto> {

    public TaskController(TaskService taskService,
                                    TaskMapper taskMapper) {
        super(taskService, taskMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TaskDto>> getAllTasks(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TaskDto>> searchTasks(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Task.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody TaskDto taskDto,
            UriComponentsBuilder uriBuilder) {

        Task entity = mapper.toEntity(taskDto);
        Task saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/tasks/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TaskDto>> createAllTasks(
            @Valid @RequestBody List<TaskDto> taskDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Task> entities = mapper.toEntityList(taskDtoList);
        List<Task> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/tasks").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDto taskDto) {

        Task entityToUpdate = mapper.toEntity(taskDto);
        Task updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return doDelete(id);
    }
}