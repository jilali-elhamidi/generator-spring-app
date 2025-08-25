package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentRatingAgeGroupDto;
import com.example.modules.entertainment_ecosystem.model.ContentRatingAgeGroup;
import com.example.modules.entertainment_ecosystem.mapper.ContentRatingAgeGroupMapper;
import com.example.modules.entertainment_ecosystem.service.ContentRatingAgeGroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contentratingagegroups")
public class ContentRatingAgeGroupController {

    private final ContentRatingAgeGroupService contentratingagegroupService;
    private final ContentRatingAgeGroupMapper contentratingagegroupMapper;

    public ContentRatingAgeGroupController(ContentRatingAgeGroupService contentratingagegroupService,
                                    ContentRatingAgeGroupMapper contentratingagegroupMapper) {
        this.contentratingagegroupService = contentratingagegroupService;
        this.contentratingagegroupMapper = contentratingagegroupMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContentRatingAgeGroupDto>> getAllContentRatingAgeGroups() {
        List<ContentRatingAgeGroup> entities = contentratingagegroupService.findAll();
        return ResponseEntity.ok(contentratingagegroupMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentRatingAgeGroupDto> getContentRatingAgeGroupById(@PathVariable Long id) {
        return contentratingagegroupService.findById(id)
                .map(contentratingagegroupMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContentRatingAgeGroupDto> createContentRatingAgeGroup(
            @Valid @RequestBody ContentRatingAgeGroupDto contentratingagegroupDto,
            UriComponentsBuilder uriBuilder) {

        ContentRatingAgeGroup entity = contentratingagegroupMapper.toEntity(contentratingagegroupDto);
        ContentRatingAgeGroup saved = contentratingagegroupService.save(entity);

        URI location = uriBuilder
                                .path("/api/contentratingagegroups/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(contentratingagegroupMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContentRatingAgeGroupDto>> createAllContentRatingAgeGroups(
            @Valid @RequestBody List<ContentRatingAgeGroupDto> contentratingagegroupDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ContentRatingAgeGroup> entities = contentratingagegroupMapper.toEntityList(contentratingagegroupDtoList);
        List<ContentRatingAgeGroup> savedEntities = contentratingagegroupService.saveAll(entities);

        URI location = uriBuilder.path("/api/contentratingagegroups").build().toUri();

        return ResponseEntity.created(location).body(contentratingagegroupMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentRatingAgeGroupDto> updateContentRatingAgeGroup(
            @PathVariable Long id,
            @Valid @RequestBody ContentRatingAgeGroupDto contentratingagegroupDto) {


        ContentRatingAgeGroup entityToUpdate = contentratingagegroupMapper.toEntity(contentratingagegroupDto);
        ContentRatingAgeGroup updatedEntity = contentratingagegroupService.update(id, entityToUpdate);

        return ResponseEntity.ok(contentratingagegroupMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentRatingAgeGroup(@PathVariable Long id) {
        boolean deleted = contentratingagegroupService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}