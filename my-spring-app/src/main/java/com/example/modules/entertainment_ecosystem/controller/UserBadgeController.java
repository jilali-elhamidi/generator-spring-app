package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserBadgeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserBadgeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserBadge;
import com.example.modules.entertainment_ecosystem.mapper.UserBadgeMapper;
import com.example.modules.entertainment_ecosystem.service.UserBadgeService;
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
 * Controller for managing UserBadge entities.
 */
@RestController
@RequestMapping("/api/userbadges")
public class UserBadgeController extends BaseController<UserBadge, UserBadgeDto, UserBadgeSimpleDto> {

    public UserBadgeController(UserBadgeService userbadgeService,
                                    UserBadgeMapper userbadgeMapper) {
        super(userbadgeService, userbadgeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserBadgeDto>> getAllUserBadges(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserBadgeDto>> searchUserBadges(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserBadge.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserBadgeDto> getUserBadgeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserBadgeDto> createUserBadge(
            @Valid @RequestBody UserBadgeDto userbadgeDto,
            UriComponentsBuilder uriBuilder) {

        UserBadge entity = mapper.toEntity(userbadgeDto);
        UserBadge saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userbadges/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserBadgeDto>> createAllUserBadges(
            @Valid @RequestBody List<UserBadgeDto> userbadgeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserBadge> entities = mapper.toEntityList(userbadgeDtoList);
        List<UserBadge> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userbadges").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserBadgeDto> updateUserBadge(
            @PathVariable Long id,
            @Valid @RequestBody UserBadgeDto userbadgeDto) {

        UserBadge entityToUpdate = mapper.toEntity(userbadgeDto);
        UserBadge updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserBadge(@PathVariable Long id) {
        return doDelete(id);
    }
}