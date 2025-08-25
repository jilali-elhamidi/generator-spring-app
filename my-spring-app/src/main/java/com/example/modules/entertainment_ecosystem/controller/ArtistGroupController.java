package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ArtistGroupDto;
import com.example.modules.entertainment_ecosystem.model.ArtistGroup;
import com.example.modules.entertainment_ecosystem.mapper.ArtistGroupMapper;
import com.example.modules.entertainment_ecosystem.service.ArtistGroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/artistgroups")
public class ArtistGroupController {

    private final ArtistGroupService artistgroupService;
    private final ArtistGroupMapper artistgroupMapper;

    public ArtistGroupController(ArtistGroupService artistgroupService,
                                    ArtistGroupMapper artistgroupMapper) {
        this.artistgroupService = artistgroupService;
        this.artistgroupMapper = artistgroupMapper;
    }

    @GetMapping
    public ResponseEntity<List<ArtistGroupDto>> getAllArtistGroups() {
        List<ArtistGroup> entities = artistgroupService.findAll();
        return ResponseEntity.ok(artistgroupMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistGroupDto> getArtistGroupById(@PathVariable Long id) {
        return artistgroupService.findById(id)
                .map(artistgroupMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArtistGroupDto> createArtistGroup(
            @Valid @RequestBody ArtistGroupDto artistgroupDto,
            UriComponentsBuilder uriBuilder) {

        ArtistGroup entity = artistgroupMapper.toEntity(artistgroupDto);
        ArtistGroup saved = artistgroupService.save(entity);

        URI location = uriBuilder
                                .path("/api/artistgroups/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(artistgroupMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ArtistGroupDto>> createAllArtistGroups(
            @Valid @RequestBody List<ArtistGroupDto> artistgroupDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ArtistGroup> entities = artistgroupMapper.toEntityList(artistgroupDtoList);
        List<ArtistGroup> savedEntities = artistgroupService.saveAll(entities);

        URI location = uriBuilder.path("/api/artistgroups").build().toUri();

        return ResponseEntity.created(location).body(artistgroupMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistGroupDto> updateArtistGroup(
            @PathVariable Long id,
            @Valid @RequestBody ArtistGroupDto artistgroupDto) {


        ArtistGroup entityToUpdate = artistgroupMapper.toEntity(artistgroupDto);
        ArtistGroup updatedEntity = artistgroupService.update(id, entityToUpdate);

        return ResponseEntity.ok(artistgroupMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistGroup(@PathVariable Long id) {
        boolean deleted = artistgroupService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}