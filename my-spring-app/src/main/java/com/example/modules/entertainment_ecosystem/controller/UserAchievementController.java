package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserAchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserAchievementSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.mapper.UserAchievementMapper;
import com.example.modules.entertainment_ecosystem.service.UserAchievementService;
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
 * Controller for managing UserAchievement entities.
 */
@RestController
@RequestMapping("/api/userachievements")
public class UserAchievementController extends BaseController<UserAchievement, UserAchievementDto, UserAchievementSimpleDto> {

    public UserAchievementController(UserAchievementService userachievementService,
                                    UserAchievementMapper userachievementMapper) {
        super(userachievementService, userachievementMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserAchievementDto>> getAllUserAchievements(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserAchievementDto>> searchUserAchievements(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserAchievement.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAchievementDto> getUserAchievementById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserAchievementDto> createUserAchievement(
            @Valid @RequestBody UserAchievementDto userachievementDto,
            UriComponentsBuilder uriBuilder) {

        UserAchievement entity = mapper.toEntity(userachievementDto);
        UserAchievement saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userachievements/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserAchievementDto>> createAllUserAchievements(
            @Valid @RequestBody List<UserAchievementDto> userachievementDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserAchievement> entities = mapper.toEntityList(userachievementDtoList);
        List<UserAchievement> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userachievements").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAchievementDto> updateUserAchievement(
            @PathVariable Long id,
            @Valid @RequestBody UserAchievementDto userachievementDto) {

        UserAchievement entityToUpdate = mapper.toEntity(userachievementDto);
        UserAchievement updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAchievement(@PathVariable Long id) {
        return doDelete(id);
    }
}