package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PlaylistItemDto;
import com.example.modules.entertainment_ecosystem.model.PlaylistItem;
import com.example.modules.entertainment_ecosystem.mapper.PlaylistItemMapper;
import com.example.modules.entertainment_ecosystem.service.PlaylistItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/playlistitems")
public class PlaylistItemController {

    private final PlaylistItemService playlistitemService;
    private final PlaylistItemMapper playlistitemMapper;

    public PlaylistItemController(PlaylistItemService playlistitemService,
                                    PlaylistItemMapper playlistitemMapper) {
        this.playlistitemService = playlistitemService;
        this.playlistitemMapper = playlistitemMapper;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistItemDto>> getAllPlaylistItems() {
        List<PlaylistItem> entities = playlistitemService.findAll();
        return ResponseEntity.ok(playlistitemMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistItemDto> getPlaylistItemById(@PathVariable Long id) {
        return playlistitemService.findById(id)
                .map(playlistitemMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlaylistItemDto> createPlaylistItem(
            @Valid @RequestBody PlaylistItemDto playlistitemDto,
            UriComponentsBuilder uriBuilder) {

        PlaylistItem entity = playlistitemMapper.toEntity(playlistitemDto);
        PlaylistItem saved = playlistitemService.save(entity);

        URI location = uriBuilder
                                .path("/api/playlistitems/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(playlistitemMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistItemDto> updatePlaylistItem(
            @PathVariable Long id,
            @Valid @RequestBody PlaylistItemDto playlistitemDto) {


        PlaylistItem entityToUpdate = playlistitemMapper.toEntity(playlistitemDto);
        PlaylistItem updatedEntity = playlistitemService.update(id, entityToUpdate);

        return ResponseEntity.ok(playlistitemMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylistItem(@PathVariable Long id) {
        boolean deleted = playlistitemService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}