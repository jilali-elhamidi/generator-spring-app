package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentRatingAgeGroupDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingAgeGroupSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ContentRatingAgeGroup;
import com.example.modules.entertainment_ecosystem.mapper.ContentRatingAgeGroupMapper;
import com.example.modules.entertainment_ecosystem.service.ContentRatingAgeGroupService;
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
 * Controller for managing ContentRatingAgeGroup entities.
 */
@RestController
@RequestMapping("/api/contentratingagegroups")
public class ContentRatingAgeGroupController extends BaseController<ContentRatingAgeGroup, ContentRatingAgeGroupDto, ContentRatingAgeGroupSimpleDto> {

    public ContentRatingAgeGroupController(ContentRatingAgeGroupService contentratingagegroupService,
                                    ContentRatingAgeGroupMapper contentratingagegroupMapper) {
        super(contentratingagegroupService, contentratingagegroupMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ContentRatingAgeGroupDto>> getAllContentRatingAgeGroups(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContentRatingAgeGroupDto>> searchContentRatingAgeGroups(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ContentRatingAgeGroup.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentRatingAgeGroupDto> getContentRatingAgeGroupById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ContentRatingAgeGroupDto> createContentRatingAgeGroup(
            @Valid @RequestBody ContentRatingAgeGroupDto contentratingagegroupDto,
            UriComponentsBuilder uriBuilder) {

        ContentRatingAgeGroup entity = mapper.toEntity(contentratingagegroupDto);
        ContentRatingAgeGroup saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/contentratingagegroups/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContentRatingAgeGroupDto>> createAllContentRatingAgeGroups(
            @Valid @RequestBody List<ContentRatingAgeGroupDto> contentratingagegroupDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ContentRatingAgeGroup> entities = mapper.toEntityList(contentratingagegroupDtoList);
        List<ContentRatingAgeGroup> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/contentratingagegroups").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentRatingAgeGroupDto> updateContentRatingAgeGroup(
            @PathVariable Long id,
            @Valid @RequestBody ContentRatingAgeGroupDto contentratingagegroupDto) {

        ContentRatingAgeGroup entityToUpdate = mapper.toEntity(contentratingagegroupDto);
        ContentRatingAgeGroup updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentRatingAgeGroup(@PathVariable Long id) {
        return doDelete(id);
    }
}