package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SocialMediaPlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SocialMediaPlatformSimpleDto;
import com.example.modules.entertainment_ecosystem.model.SocialMediaPlatform;
import com.example.modules.entertainment_ecosystem.mapper.SocialMediaPlatformMapper;
import com.example.modules.entertainment_ecosystem.service.SocialMediaPlatformService;
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
 * Controller for managing SocialMediaPlatform entities.
 */
@RestController
@RequestMapping("/api/socialmediaplatforms")
public class SocialMediaPlatformController extends BaseController<SocialMediaPlatform, SocialMediaPlatformDto, SocialMediaPlatformSimpleDto> {

    public SocialMediaPlatformController(SocialMediaPlatformService socialmediaplatformService,
                                    SocialMediaPlatformMapper socialmediaplatformMapper) {
        super(socialmediaplatformService, socialmediaplatformMapper);
    }

    @GetMapping
    public ResponseEntity<Page<SocialMediaPlatformDto>> getAllSocialMediaPlatforms(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SocialMediaPlatformDto>> searchSocialMediaPlatforms(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(SocialMediaPlatform.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialMediaPlatformDto> getSocialMediaPlatformById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<SocialMediaPlatformDto> createSocialMediaPlatform(
            @Valid @RequestBody SocialMediaPlatformDto socialmediaplatformDto,
            UriComponentsBuilder uriBuilder) {

        SocialMediaPlatform entity = mapper.toEntity(socialmediaplatformDto);
        SocialMediaPlatform saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/socialmediaplatforms/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SocialMediaPlatformDto>> createAllSocialMediaPlatforms(
            @Valid @RequestBody List<SocialMediaPlatformDto> socialmediaplatformDtoList,
            UriComponentsBuilder uriBuilder) {

        List<SocialMediaPlatform> entities = mapper.toEntityList(socialmediaplatformDtoList);
        List<SocialMediaPlatform> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/socialmediaplatforms").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialMediaPlatformDto> updateSocialMediaPlatform(
            @PathVariable Long id,
            @Valid @RequestBody SocialMediaPlatformDto socialmediaplatformDto) {

        SocialMediaPlatform entityToUpdate = mapper.toEntity(socialmediaplatformDto);
        SocialMediaPlatform updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialMediaPlatform(@PathVariable Long id) {
        return doDelete(id);
    }
}