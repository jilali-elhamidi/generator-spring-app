package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserPreferenceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPreferenceSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserPreference;
import com.example.modules.entertainment_ecosystem.mapper.UserPreferenceMapper;
import com.example.modules.entertainment_ecosystem.service.UserPreferenceService;
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
 * Controller for managing UserPreference entities.
 */
@RestController
@RequestMapping("/api/userpreferences")
public class UserPreferenceController extends BaseController<UserPreference, UserPreferenceDto, UserPreferenceSimpleDto> {

    public UserPreferenceController(UserPreferenceService userpreferenceService,
                                    UserPreferenceMapper userpreferenceMapper) {
        super(userpreferenceService, userpreferenceMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserPreferenceDto>> getAllUserPreferences(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserPreferenceDto>> searchUserPreferences(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserPreference.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPreferenceDto> getUserPreferenceById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserPreferenceDto> createUserPreference(
            @Valid @RequestBody UserPreferenceDto userpreferenceDto,
            UriComponentsBuilder uriBuilder) {

        UserPreference entity = mapper.toEntity(userpreferenceDto);
        UserPreference saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userpreferences/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserPreferenceDto>> createAllUserPreferences(
            @Valid @RequestBody List<UserPreferenceDto> userpreferenceDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserPreference> entities = mapper.toEntityList(userpreferenceDtoList);
        List<UserPreference> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userpreferences").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPreferenceDto> updateUserPreference(
            @PathVariable Long id,
            @Valid @RequestBody UserPreferenceDto userpreferenceDto) {

        UserPreference entityToUpdate = mapper.toEntity(userpreferenceDto);
        UserPreference updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPreference(@PathVariable Long id) {
        return doDelete(id);
    }
}