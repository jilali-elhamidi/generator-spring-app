package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PodcastCategoryDto;
import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import com.example.modules.entertainment_ecosystem.mapper.PodcastCategoryMapper;
import com.example.modules.entertainment_ecosystem.service.PodcastCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/podcastcategorys")
public class PodcastCategoryController {

    private final PodcastCategoryService podcastcategoryService;
    private final PodcastCategoryMapper podcastcategoryMapper;

    public PodcastCategoryController(PodcastCategoryService podcastcategoryService,
                                    PodcastCategoryMapper podcastcategoryMapper) {
        this.podcastcategoryService = podcastcategoryService;
        this.podcastcategoryMapper = podcastcategoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<PodcastCategoryDto>> getAllPodcastCategorys() {
        List<PodcastCategory> entities = podcastcategoryService.findAll();
        return ResponseEntity.ok(podcastcategoryMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastCategoryDto> getPodcastCategoryById(@PathVariable Long id) {
        return podcastcategoryService.findById(id)
                .map(podcastcategoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PodcastCategoryDto> createPodcastCategory(
            @Valid @RequestBody PodcastCategoryDto podcastcategoryDto,
            UriComponentsBuilder uriBuilder) {

        PodcastCategory entity = podcastcategoryMapper.toEntity(podcastcategoryDto);
        PodcastCategory saved = podcastcategoryService.save(entity);
        URI location = uriBuilder.path("/api/podcastcategorys/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(podcastcategoryMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<PodcastCategoryDto> updatePodcastCategory(
                @PathVariable Long id,
                @RequestBody PodcastCategoryDto podcastcategoryDto) {

                // Transformer le DTO en entity pour le service
                PodcastCategory entityToUpdate = podcastcategoryMapper.toEntity(podcastcategoryDto);

                // Appel du service update
                PodcastCategory updatedEntity = podcastcategoryService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                PodcastCategoryDto updatedDto = podcastcategoryMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcastCategory(@PathVariable Long id) {
        podcastcategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}