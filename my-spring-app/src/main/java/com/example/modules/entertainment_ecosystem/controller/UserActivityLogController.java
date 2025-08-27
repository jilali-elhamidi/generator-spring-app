package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserActivityLogDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserActivityLogSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.mapper.UserActivityLogMapper;
import com.example.modules.entertainment_ecosystem.service.UserActivityLogService;
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
 * Controller for managing UserActivityLog entities.
 */
@RestController
@RequestMapping("/api/useractivitylogs")
public class UserActivityLogController extends BaseController<UserActivityLog, UserActivityLogDto, UserActivityLogSimpleDto> {

    public UserActivityLogController(UserActivityLogService useractivitylogService,
                                    UserActivityLogMapper useractivitylogMapper) {
        super(useractivitylogService, useractivitylogMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserActivityLogDto>> getAllUserActivityLogs(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserActivityLogDto>> searchUserActivityLogs(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserActivityLog.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserActivityLogDto> getUserActivityLogById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserActivityLogDto> createUserActivityLog(
            @Valid @RequestBody UserActivityLogDto useractivitylogDto,
            UriComponentsBuilder uriBuilder) {

        UserActivityLog entity = mapper.toEntity(useractivitylogDto);
        UserActivityLog saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/useractivitylogs/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserActivityLogDto>> createAllUserActivityLogs(
            @Valid @RequestBody List<UserActivityLogDto> useractivitylogDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserActivityLog> entities = mapper.toEntityList(useractivitylogDtoList);
        List<UserActivityLog> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/useractivitylogs").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserActivityLogDto> updateUserActivityLog(
            @PathVariable Long id,
            @Valid @RequestBody UserActivityLogDto useractivitylogDto) {

        UserActivityLog entityToUpdate = mapper.toEntity(useractivitylogDto);
        UserActivityLog updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserActivityLog(@PathVariable Long id) {
        return doDelete(id);
    }
}