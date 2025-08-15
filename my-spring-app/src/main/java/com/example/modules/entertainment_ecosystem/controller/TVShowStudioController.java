package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TVShowStudioDto;
import com.example.modules.entertainment_ecosystem.model.TVShowStudio;
import com.example.modules.entertainment_ecosystem.mapper.TVShowStudioMapper;
import com.example.modules.entertainment_ecosystem.service.TVShowStudioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tvshowstudios")
public class TVShowStudioController {

    private final TVShowStudioService tvshowstudioService;
    private final TVShowStudioMapper tvshowstudioMapper;

    public TVShowStudioController(TVShowStudioService tvshowstudioService,
                                    TVShowStudioMapper tvshowstudioMapper) {
        this.tvshowstudioService = tvshowstudioService;
        this.tvshowstudioMapper = tvshowstudioMapper;
    }

    @GetMapping
    public ResponseEntity<List<TVShowStudioDto>> getAllTVShowStudios() {
        List<TVShowStudio> entities = tvshowstudioService.findAll();
        return ResponseEntity.ok(tvshowstudioMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TVShowStudioDto> getTVShowStudioById(@PathVariable Long id) {
        return tvshowstudioService.findById(id)
                .map(tvshowstudioMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TVShowStudioDto> createTVShowStudio(
            @Valid @RequestBody TVShowStudioDto tvshowstudioDto,
            UriComponentsBuilder uriBuilder) {

        TVShowStudio entity = tvshowstudioMapper.toEntity(tvshowstudioDto);
        TVShowStudio saved = tvshowstudioService.save(entity);
        URI location = uriBuilder.path("/api/tvshowstudios/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(tvshowstudioMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<TVShowStudioDto> updateTVShowStudio(
                @PathVariable Long id,
                @RequestBody TVShowStudioDto tvshowstudioDto) {

                // Transformer le DTO en entity pour le service
                TVShowStudio entityToUpdate = tvshowstudioMapper.toEntity(tvshowstudioDto);

                // Appel du service update
                TVShowStudio updatedEntity = tvshowstudioService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                TVShowStudioDto updatedDto = tvshowstudioMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTVShowStudio(@PathVariable Long id) {
        tvshowstudioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}