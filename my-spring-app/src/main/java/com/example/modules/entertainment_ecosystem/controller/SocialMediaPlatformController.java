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

        URI location = uriBuilder
                                .path("/api/socialmediaplatforms/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(socialmediaplatformMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialMediaPlatformDto> updateSocialMediaPlatform(
            @PathVariable Long id,
            @Valid @RequestBody SocialMediaPlatformDto socialmediaplatformDto) {


        SocialMediaPlatform entityToUpdate = socialmediaplatformMapper.toEntity(socialmediaplatformDto);
        SocialMediaPlatform updatedEntity = socialmediaplatformService.update(id, entityToUpdate);

        return ResponseEntity.ok(socialmediaplatformMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialMediaPlatform(@PathVariable Long id) {
        boolean deleted = socialmediaplatformService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}