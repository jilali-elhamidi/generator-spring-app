package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserPreferenceDto;
import com.example.modules.entertainment_ecosystem.model.UserPreference;
import com.example.modules.entertainment_ecosystem.mapper.UserPreferenceMapper;
import com.example.modules.entertainment_ecosystem.service.UserPreferenceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userpreferences")
public class UserPreferenceController {

    private final UserPreferenceService userpreferenceService;
    private final UserPreferenceMapper userpreferenceMapper;

    public UserPreferenceController(UserPreferenceService userpreferenceService,
                                    UserPreferenceMapper userpreferenceMapper) {
        this.userpreferenceService = userpreferenceService;
        this.userpreferenceMapper = userpreferenceMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserPreferenceDto>> getAllUserPreferences() {
        List<UserPreference> entities = userpreferenceService.findAll();
        return ResponseEntity.ok(userpreferenceMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPreferenceDto> getUserPreferenceById(@PathVariable Long id) {
        return userpreferenceService.findById(id)
                .map(userpreferenceMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserPreferenceDto> createUserPreference(
            @Valid @RequestBody UserPreferenceDto userpreferenceDto,
            UriComponentsBuilder uriBuilder) {

        UserPreference entity = userpreferenceMapper.toEntity(userpreferenceDto);
        UserPreference saved = userpreferenceService.save(entity);

        URI location = uriBuilder
                                .path("/api/userpreferences/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userpreferenceMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserPreferenceDto>> createAllUserPreferences(
            @Valid @RequestBody List<UserPreferenceDto> userpreferenceDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserPreference> entities = userpreferenceMapper.toEntityList(userpreferenceDtoList);
        List<UserPreference> savedEntities = userpreferenceService.saveAll(entities);

        URI location = uriBuilder.path("/api/userpreferences").build().toUri();

        return ResponseEntity.created(location).body(userpreferenceMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPreferenceDto> updateUserPreference(
            @PathVariable Long id,
            @Valid @RequestBody UserPreferenceDto userpreferenceDto) {


        UserPreference entityToUpdate = userpreferenceMapper.toEntity(userpreferenceDto);
        UserPreference updatedEntity = userpreferenceService.update(id, entityToUpdate);

        return ResponseEntity.ok(userpreferenceMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPreference(@PathVariable Long id) {
        boolean deleted = userpreferenceService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}