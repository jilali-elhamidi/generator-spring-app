package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.OnlinePlatformDto;
import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;
import com.example.modules.entertainment_ecosystem.mapper.OnlinePlatformMapper;
import com.example.modules.entertainment_ecosystem.service.OnlinePlatformService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/onlineplatforms")
public class OnlinePlatformController {

    private final OnlinePlatformService onlineplatformService;
    private final OnlinePlatformMapper onlineplatformMapper;

    public OnlinePlatformController(OnlinePlatformService onlineplatformService,
                                    OnlinePlatformMapper onlineplatformMapper) {
        this.onlineplatformService = onlineplatformService;
        this.onlineplatformMapper = onlineplatformMapper;
    }

    @GetMapping
    public ResponseEntity<List<OnlinePlatformDto>> getAllOnlinePlatforms() {
        List<OnlinePlatform> entities = onlineplatformService.findAll();
        return ResponseEntity.ok(onlineplatformMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnlinePlatformDto> getOnlinePlatformById(@PathVariable Long id) {
        return onlineplatformService.findById(id)
                .map(onlineplatformMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OnlinePlatformDto> createOnlinePlatform(
            @Valid @RequestBody OnlinePlatformDto onlineplatformDto,
            UriComponentsBuilder uriBuilder) {

        OnlinePlatform entity = onlineplatformMapper.toEntity(onlineplatformDto);
        OnlinePlatform saved = onlineplatformService.save(entity);

        URI location = uriBuilder
                                .path("/api/onlineplatforms/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(onlineplatformMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<OnlinePlatformDto>> createAllOnlinePlatforms(
            @Valid @RequestBody List<OnlinePlatformDto> onlineplatformDtoList,
            UriComponentsBuilder uriBuilder) {

        List<OnlinePlatform> entities = onlineplatformMapper.toEntityList(onlineplatformDtoList);
        List<OnlinePlatform> savedEntities = onlineplatformService.saveAll(entities);

        URI location = uriBuilder.path("/api/onlineplatforms").build().toUri();

        return ResponseEntity.created(location).body(onlineplatformMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnlinePlatformDto> updateOnlinePlatform(
            @PathVariable Long id,
            @Valid @RequestBody OnlinePlatformDto onlineplatformDto) {


        OnlinePlatform entityToUpdate = onlineplatformMapper.toEntity(onlineplatformDto);
        OnlinePlatform updatedEntity = onlineplatformService.update(id, entityToUpdate);

        return ResponseEntity.ok(onlineplatformMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnlinePlatform(@PathVariable Long id) {
        boolean deleted = onlineplatformService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}