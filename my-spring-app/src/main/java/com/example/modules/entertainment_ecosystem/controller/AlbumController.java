package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AlbumDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AlbumSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.mapper.AlbumMapper;
import com.example.modules.entertainment_ecosystem.service.AlbumService;
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
 * Controller for managing Album entities.
 */
@RestController
@RequestMapping("/api/albums")
public class AlbumController extends BaseController<Album, AlbumDto, AlbumSimpleDto> {

    public AlbumController(AlbumService albumService,
                                    AlbumMapper albumMapper) {
        super(albumService, albumMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AlbumDto>> getAllAlbums(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AlbumDto>> searchAlbums(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Album.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AlbumDto> createAlbum(
            @Valid @RequestBody AlbumDto albumDto,
            UriComponentsBuilder uriBuilder) {

        Album entity = mapper.toEntity(albumDto);
        Album saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/albums/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AlbumDto>> createAllAlbums(
            @Valid @RequestBody List<AlbumDto> albumDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Album> entities = mapper.toEntityList(albumDtoList);
        List<Album> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/albums").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDto> updateAlbum(
            @PathVariable Long id,
            @Valid @RequestBody AlbumDto albumDto) {

        Album entityToUpdate = mapper.toEntity(albumDto);
        Album updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        return doDelete(id);
    }
}