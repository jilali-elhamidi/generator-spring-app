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
        URI location = uriBuilder.path("/api/userpreferences/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(userpreferenceMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserPreferenceDto> updateUserPreference(
                @PathVariable Long id,
                @RequestBody UserPreferenceDto userpreferenceDto) {

                // Transformer le DTO en entity pour le service
                UserPreference entityToUpdate = userpreferenceMapper.toEntity(userpreferenceDto);

                // Appel du service update
                UserPreference updatedEntity = userpreferenceService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserPreferenceDto updatedDto = userpreferenceMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteUserPreference(@PathVariable Long id) {
                    boolean deleted = userpreferenceService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}