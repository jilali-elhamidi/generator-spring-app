package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PodcastCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastCategorySimpleDto;
import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import com.example.modules.entertainment_ecosystem.mapper.PodcastCategoryMapper;
import com.example.modules.entertainment_ecosystem.service.PodcastCategoryService;
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
 * Controller for managing PodcastCategory entities.
 */
@RestController
@RequestMapping("/api/podcastcategorys")
public class PodcastCategoryController extends BaseController<PodcastCategory, PodcastCategoryDto, PodcastCategorySimpleDto> {

    public PodcastCategoryController(PodcastCategoryService podcastcategoryService,
                                    PodcastCategoryMapper podcastcategoryMapper) {
        super(podcastcategoryService, podcastcategoryMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PodcastCategoryDto>> getAllPodcastCategorys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PodcastCategoryDto>> searchPodcastCategorys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(PodcastCategory.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastCategoryDto> getPodcastCategoryById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PodcastCategoryDto> createPodcastCategory(
            @Valid @RequestBody PodcastCategoryDto podcastcategoryDto,
            UriComponentsBuilder uriBuilder) {

        PodcastCategory entity = mapper.toEntity(podcastcategoryDto);
        PodcastCategory saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/podcastcategorys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PodcastCategoryDto>> createAllPodcastCategorys(
            @Valid @RequestBody List<PodcastCategoryDto> podcastcategoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<PodcastCategory> entities = mapper.toEntityList(podcastcategoryDtoList);
        List<PodcastCategory> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/podcastcategorys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastCategoryDto> updatePodcastCategory(
            @PathVariable Long id,
            @Valid @RequestBody PodcastCategoryDto podcastcategoryDto) {

        PodcastCategory entityToUpdate = mapper.toEntity(podcastcategoryDto);
        PodcastCategory updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcastCategory(@PathVariable Long id) {
        return doDelete(id);
    }
}