package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserLevelDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserLevelSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserLevel;
import com.example.modules.entertainment_ecosystem.mapper.UserLevelMapper;
import com.example.modules.entertainment_ecosystem.service.UserLevelService;
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
 * Controller for managing UserLevel entities.
 */
@RestController
@RequestMapping("/api/userlevels")
public class UserLevelController extends BaseController<UserLevel, UserLevelDto, UserLevelSimpleDto> {

    public UserLevelController(UserLevelService userlevelService,
                                    UserLevelMapper userlevelMapper) {
        super(userlevelService, userlevelMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserLevelDto>> getAllUserLevels(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserLevelDto>> searchUserLevels(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserLevel.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLevelDto> getUserLevelById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserLevelDto> createUserLevel(
            @Valid @RequestBody UserLevelDto userlevelDto,
            UriComponentsBuilder uriBuilder) {

        UserLevel entity = mapper.toEntity(userlevelDto);
        UserLevel saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userlevels/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserLevelDto>> createAllUserLevels(
            @Valid @RequestBody List<UserLevelDto> userlevelDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserLevel> entities = mapper.toEntityList(userlevelDtoList);
        List<UserLevel> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userlevels").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserLevelDto> updateUserLevel(
            @PathVariable Long id,
            @Valid @RequestBody UserLevelDto userlevelDto) {

        UserLevel entityToUpdate = mapper.toEntity(userlevelDto);
        UserLevel updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserLevel(@PathVariable Long id) {
        return doDelete(id);
    }
}