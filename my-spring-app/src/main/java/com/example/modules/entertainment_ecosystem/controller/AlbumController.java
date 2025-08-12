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
            @Valid @RequestBody AlbumDto albumDto) {

        try {
            Album updatedEntity = albumService.update(
                    id,
                    albumMapper.toEntity(albumDto)
            );
            return ResponseEntity.ok(albumMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}