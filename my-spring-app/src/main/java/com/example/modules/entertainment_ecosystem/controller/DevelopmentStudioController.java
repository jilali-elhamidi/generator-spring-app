package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.DevelopmentStudioDto;
import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.mapper.DevelopmentStudioMapper;
import com.example.modules.entertainment_ecosystem.service.DevelopmentStudioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/developmentstudios")
public class DevelopmentStudioController {

    private final DevelopmentStudioService developmentstudioService;
    private final DevelopmentStudioMapper developmentstudioMapper;

    public DevelopmentStudioController(DevelopmentStudioService developmentstudioService,
                                    DevelopmentStudioMapper developmentstudioMapper) {
        this.developmentstudioService = developmentstudioService;
        this.developmentstudioMapper = developmentstudioMapper;
    }

    @GetMapping
    public ResponseEntity<List<DevelopmentStudioDto>> getAllDevelopmentStudios() {
        List<DevelopmentStudio> entities = developmentstudioService.findAll();
        return ResponseEntity.ok(developmentstudioMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentStudioDto> getDevelopmentStudioById(@PathVariable Long id) {
        return developmentstudioService.findById(id)
                .map(developmentstudioMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DevelopmentStudioDto> createDevelopmentStudio(
            @Valid @RequestBody DevelopmentStudioDto developmentstudioDto,
            UriComponentsBuilder uriBuilder) {

        DevelopmentStudio entity = developmentstudioMapper.toEntity(developmentstudioDto);
        DevelopmentStudio saved = developmentstudioService.save(entity);
        URI location = uriBuilder.path("/api/developmentstudios/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(developmentstudioMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<DevelopmentStudioDto> updateDevelopmentStudio(
                @PathVariable Long id,
                @RequestBody DevelopmentStudioDto developmentstudioDto) {

                // Transformer le DTO en entity pour le service
                DevelopmentStudio entityToUpdate = developmentstudioMapper.toEntity(developmentstudioDto);

                // Appel du service update
                DevelopmentStudio updatedEntity = developmentstudioService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                DevelopmentStudioDto updatedDto = developmentstudioMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevelopmentStudio(@PathVariable Long id) {
        developmentstudioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}