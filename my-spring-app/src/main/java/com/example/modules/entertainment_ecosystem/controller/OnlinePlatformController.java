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
        URI location = uriBuilder.path("/api/onlineplatforms/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(onlineplatformMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<OnlinePlatformDto> updateOnlinePlatform(
                @PathVariable Long id,
                @Valid @RequestBody OnlinePlatformDto onlineplatformDto) {

                try {
                // Récupérer l'entité existante avec Optional
                OnlinePlatform existing = onlineplatformService.findById(id)
                .orElseThrow(() -> new RuntimeException("OnlinePlatform not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                onlineplatformMapper.updateEntityFromDto(onlineplatformDto, existing);

                // Sauvegarde
                OnlinePlatform updatedEntity = onlineplatformService.save(existing);

                return ResponseEntity.ok(onlineplatformMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnlinePlatform(@PathVariable Long id) {
        onlineplatformService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}