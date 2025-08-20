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
        URI location = uriBuilder.path("/api/artistgroups/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(artistgroupMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ArtistGroupDto> updateArtistGroup(
                @PathVariable Long id,
                @RequestBody ArtistGroupDto artistgroupDto) {

                // Transformer le DTO en entity pour le service
                ArtistGroup entityToUpdate = artistgroupMapper.toEntity(artistgroupDto);

                // Appel du service update
                ArtistGroup updatedEntity = artistgroupService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ArtistGroupDto updatedDto = artistgroupMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteArtistGroup(@PathVariable Long id) {
                    boolean deleted = artistgroupService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}