package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PlaylistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Playlist;
import com.example.modules.entertainment_ecosystem.mapper.PlaylistMapper;
import com.example.modules.entertainment_ecosystem.service.PlaylistService;
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
 * Controller for managing Playlist entities.
 */
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController extends BaseController<Playlist, PlaylistDto, PlaylistSimpleDto> {

    public PlaylistController(PlaylistService playlistService,
                                    PlaylistMapper playlistMapper) {
        super(playlistService, playlistMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PlaylistDto>> getAllPlaylists(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PlaylistDto>> searchPlaylists(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Playlist.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDto> getPlaylistById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PlaylistDto> createPlaylist(
            @Valid @RequestBody PlaylistDto playlistDto,
            UriComponentsBuilder uriBuilder) {

        Playlist entity = mapper.toEntity(playlistDto);
        Playlist saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/playlists/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PlaylistDto>> createAllPlaylists(
            @Valid @RequestBody List<PlaylistDto> playlistDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Playlist> entities = mapper.toEntityList(playlistDtoList);
        List<Playlist> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/playlists").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDto> updatePlaylist(
            @PathVariable Long id,
            @Valid @RequestBody PlaylistDto playlistDto) {

        Playlist entityToUpdate = mapper.toEntity(playlistDto);
        Playlist updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        return doDelete(id);
    }
}