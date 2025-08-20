package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SocialMediaPlatformDto;
import com.example.modules.entertainment_ecosystem.model.SocialMediaPlatform;
import com.example.modules.entertainment_ecosystem.mapper.SocialMediaPlatformMapper;
import com.example.modules.entertainment_ecosystem.service.SocialMediaPlatformService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/socialmediaplatforms")
public class SocialMediaPlatformController {

    private final SocialMediaPlatformService socialmediaplatformService;
    private final SocialMediaPlatformMapper socialmediaplatformMapper;

    public SocialMediaPlatformController(SocialMediaPlatformService socialmediaplatformService,
                                    SocialMediaPlatformMapper socialmediaplatformMapper) {
        this.socialmediaplatformService = socialmediaplatformService;
        this.socialmediaplatformMapper = socialmediaplatformMapper;
    }

    @GetMapping
    public ResponseEntity<List<SocialMediaPlatformDto>> getAllSocialMediaPlatforms() {
        List<SocialMediaPlatform> entities = socialmediaplatformService.findAll();
        return ResponseEntity.ok(socialmediaplatformMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialMediaPlatformDto> getSocialMediaPlatformById(@PathVariable Long id) {
        return socialmediaplatformService.findById(id)
                .map(socialmediaplatformMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SocialMediaPlatformDto> createSocialMediaPlatform(
            @Valid @RequestBody SocialMediaPlatformDto socialmediaplatformDto,
            UriComponentsBuilder uriBuilder) {

        SocialMediaPlatform entity = socialmediaplatformMapper.toEntity(socialmediaplatformDto);
        SocialMediaPlatform saved = socialmediaplatformService.save(entity);
        URI location = uriBuilder.path("/api/socialmediaplatforms/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(socialmediaplatformMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<SocialMediaPlatformDto> updateSocialMediaPlatform(
                @PathVariable Long id,
                @RequestBody SocialMediaPlatformDto socialmediaplatformDto) {

                // Transformer le DTO en entity pour le service
                SocialMediaPlatform entityToUpdate = socialmediaplatformMapper.toEntity(socialmediaplatformDto);

                // Appel du service update
                SocialMediaPlatform updatedEntity = socialmediaplatformService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                SocialMediaPlatformDto updatedDto = socialmediaplatformMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteSocialMediaPlatform(@PathVariable Long id) {
                    boolean deleted = socialmediaplatformService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}