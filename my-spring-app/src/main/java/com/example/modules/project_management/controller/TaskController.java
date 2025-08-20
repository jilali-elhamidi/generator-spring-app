package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TaskDto;
import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.mapper.TaskMapper;
import com.example.modules.project_management.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService,
                                    TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> entities = taskService.findAll();
        return ResponseEntity.ok(taskMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return taskService.findById(id)
                .map(taskMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody TaskDto taskDto,
            UriComponentsBuilder uriBuilder) {

        Task entity = taskMapper.toEntity(taskDto);
        Task saved = taskService.save(entity);

        URI location = uriBuilder
                                .path("/api/tasks/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(taskMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDto taskDto) {


        Task entityToUpdate = taskMapper.toEntity(taskDto);
        Task updatedEntity = taskService.update(id, entityToUpdate);

        return ResponseEntity.ok(taskMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}