package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AlbumDto;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.mapper.AlbumMapper;
import com.example.modules.entertainment_ecosystem.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    public AlbumController(AlbumService albumService,
                                    AlbumMapper albumMapper) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }

    @GetMapping
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        List<Album> entities = albumService.findAll();
        return ResponseEntity.ok(albumMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable Long id) {
        return albumService.findById(id)
                .map(albumMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlbumDto> createAlbum(
            @Valid @RequestBody AlbumDto albumDto,
            UriComponentsBuilder uriBuilder) {

        Album entity = albumMapper.toEntity(albumDto);
        Album saved = albumService.save(entity);
        URI location = uriBuilder.path("/api/albums/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(albumMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<AlbumDto> updateAlbum(
                @PathVariable Long id,
                @RequestBody AlbumDto albumDto) {

                // Transformer le DTO en entity pour le service
                Album entityToUpdate = albumMapper.toEntity(albumDto);

                // Appel du service update
                Album updatedEntity = albumService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                AlbumDto updatedDto = albumMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
                    boolean deleted = albumService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}