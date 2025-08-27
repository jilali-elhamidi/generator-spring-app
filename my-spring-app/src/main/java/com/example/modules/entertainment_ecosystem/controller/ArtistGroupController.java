package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ArtistGroupDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistGroupSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ArtistGroup;
import com.example.modules.entertainment_ecosystem.mapper.ArtistGroupMapper;
import com.example.modules.entertainment_ecosystem.service.ArtistGroupService;
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
 * Controller for managing ArtistGroup entities.
 */
@RestController
@RequestMapping("/api/artistgroups")
public class ArtistGroupController extends BaseController<ArtistGroup, ArtistGroupDto, ArtistGroupSimpleDto> {

    public ArtistGroupController(ArtistGroupService artistgroupService,
                                    ArtistGroupMapper artistgroupMapper) {
        super(artistgroupService, artistgroupMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ArtistGroupDto>> getAllArtistGroups(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ArtistGroupDto>> searchArtistGroups(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ArtistGroup.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistGroupDto> getArtistGroupById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ArtistGroupDto> createArtistGroup(
            @Valid @RequestBody ArtistGroupDto artistgroupDto,
            UriComponentsBuilder uriBuilder) {

        ArtistGroup entity = mapper.toEntity(artistgroupDto);
        ArtistGroup saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/artistgroups/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ArtistGroupDto>> createAllArtistGroups(
            @Valid @RequestBody List<ArtistGroupDto> artistgroupDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ArtistGroup> entities = mapper.toEntityList(artistgroupDtoList);
        List<ArtistGroup> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/artistgroups").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistGroupDto> updateArtistGroup(
            @PathVariable Long id,
            @Valid @RequestBody ArtistGroupDto artistgroupDto) {

        ArtistGroup entityToUpdate = mapper.toEntity(artistgroupDto);
        ArtistGroup updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistGroup(@PathVariable Long id) {
        return doDelete(id);
    }
}