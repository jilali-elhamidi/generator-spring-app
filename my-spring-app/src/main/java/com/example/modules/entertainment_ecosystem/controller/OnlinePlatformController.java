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
                @RequestBody OnlinePlatformDto onlineplatformDto) {

                // Transformer le DTO en entity pour le service
                OnlinePlatform entityToUpdate = onlineplatformMapper.toEntity(onlineplatformDto);

                // Appel du service update
                OnlinePlatform updatedEntity = onlineplatformService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                OnlinePlatformDto updatedDto = onlineplatformMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnlinePlatform(@PathVariable Long id) {
        onlineplatformService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}