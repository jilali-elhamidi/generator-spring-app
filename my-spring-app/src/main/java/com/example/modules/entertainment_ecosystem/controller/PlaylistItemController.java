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
        URI location = uriBuilder.path("/api/playlistitems/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(playlistitemMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<PlaylistItemDto> updatePlaylistItem(
                @PathVariable Long id,
                @RequestBody PlaylistItemDto playlistitemDto) {

                // Transformer le DTO en entity pour le service
                PlaylistItem entityToUpdate = playlistitemMapper.toEntity(playlistitemDto);

                // Appel du service update
                PlaylistItem updatedEntity = playlistitemService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                PlaylistItemDto updatedDto = playlistitemMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylistItem(@PathVariable Long id) {
        playlistitemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}