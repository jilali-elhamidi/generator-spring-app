package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TimeLogDto;
import com.example.modules.project_management.dtosimple.TimeLogSimpleDto;
import com.example.modules.project_management.model.TimeLog;
import com.example.modules.project_management.mapper.TimeLogMapper;
import com.example.modules.project_management.service.TimeLogService;
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
 * Controller for managing TimeLog entities.
 */
@RestController
@RequestMapping("/api/timelogs")
public class TimeLogController extends BaseController<TimeLog, TimeLogDto, TimeLogSimpleDto> {

    public TimeLogController(TimeLogService timelogService,
                                    TimeLogMapper timelogMapper) {
        super(timelogService, timelogMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TimeLogDto>> getAllTimeLogs(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TimeLogDto>> searchTimeLogs(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(TimeLog.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeLogDto> getTimeLogById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TimeLogDto> createTimeLog(
            @Valid @RequestBody TimeLogDto timelogDto,
            UriComponentsBuilder uriBuilder) {

        TimeLog entity = mapper.toEntity(timelogDto);
        TimeLog saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/timelogs/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TimeLogDto>> createAllTimeLogs(
            @Valid @RequestBody List<TimeLogDto> timelogDtoList,
            UriComponentsBuilder uriBuilder) {

        List<TimeLog> entities = mapper.toEntityList(timelogDtoList);
        List<TimeLog> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/timelogs").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeLogDto> updateTimeLog(
            @PathVariable Long id,
            @Valid @RequestBody TimeLogDto timelogDto) {

        TimeLog entityToUpdate = mapper.toEntity(timelogDto);
        TimeLog updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeLog(@PathVariable Long id) {
        return doDelete(id);
    }
}