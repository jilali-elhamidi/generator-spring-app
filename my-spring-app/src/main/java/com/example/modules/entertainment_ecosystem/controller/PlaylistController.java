package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PlaylistDto;
import com.example.modules.entertainment_ecosystem.model.Playlist;
import com.example.modules.entertainment_ecosystem.mapper.PlaylistMapper;
import com.example.modules.entertainment_ecosystem.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final PlaylistMapper playlistMapper;

    public PlaylistController(PlaylistService playlistService,
                                    PlaylistMapper playlistMapper) {
        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDto>> getAllPlaylists() {
        List<Playlist> entities = playlistService.findAll();
        return ResponseEntity.ok(playlistMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDto> getPlaylistById(@PathVariable Long id) {
        return playlistService.findById(id)
                .map(playlistMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlaylistDto> createPlaylist(
            @Valid @RequestBody PlaylistDto playlistDto,
            UriComponentsBuilder uriBuilder) {

        Playlist entity = playlistMapper.toEntity(playlistDto);
        Playlist saved = playlistService.save(entity);

        URI location = uriBuilder
                                .path("/api/playlists/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(playlistMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PlaylistDto>> createAllPlaylists(
            @Valid @RequestBody List<PlaylistDto> playlistDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Playlist> entities = playlistMapper.toEntityList(playlistDtoList);
        List<Playlist> savedEntities = playlistService.saveAll(entities);

        URI location = uriBuilder.path("/api/playlists").build().toUri();

        return ResponseEntity.created(location).body(playlistMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDto> updatePlaylist(
            @PathVariable Long id,
            @Valid @RequestBody PlaylistDto playlistDto) {


        Playlist entityToUpdate = playlistMapper.toEntity(playlistDto);
        Playlist updatedEntity = playlistService.update(id, entityToUpdate);

        return ResponseEntity.ok(playlistMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        boolean deleted = playlistService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}