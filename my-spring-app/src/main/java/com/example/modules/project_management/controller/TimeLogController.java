package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TimeLogDto;
import com.example.modules.project_management.model.TimeLog;
import com.example.modules.project_management.mapper.TimeLogMapper;
import com.example.modules.project_management.service.TimeLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/timelogs")
public class TimeLogController {

    private final TimeLogService timelogService;
    private final TimeLogMapper timelogMapper;

    public TimeLogController(TimeLogService timelogService,
                                    TimeLogMapper timelogMapper) {
        this.timelogService = timelogService;
        this.timelogMapper = timelogMapper;
    }

    @GetMapping
    public ResponseEntity<List<TimeLogDto>> getAllTimeLogs() {
        List<TimeLog> entities = timelogService.findAll();
        return ResponseEntity.ok(timelogMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeLogDto> getTimeLogById(@PathVariable Long id) {
        return timelogService.findById(id)
                .map(timelogMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TimeLogDto> createTimeLog(
            @Valid @RequestBody TimeLogDto timelogDto,
            UriComponentsBuilder uriBuilder) {

        TimeLog entity = timelogMapper.toEntity(timelogDto);
        TimeLog saved = timelogService.save(entity);

        URI location = uriBuilder
                                .path("/api/timelogs/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(timelogMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeLogDto> updateTimeLog(
            @PathVariable Long id,
            @Valid @RequestBody TimeLogDto timelogDto) {


        TimeLog entityToUpdate = timelogMapper.toEntity(timelogDto);
        TimeLog updatedEntity = timelogService.update(id, entityToUpdate);

        return ResponseEntity.ok(timelogMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeLog(@PathVariable Long id) {
        boolean deleted = timelogService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}