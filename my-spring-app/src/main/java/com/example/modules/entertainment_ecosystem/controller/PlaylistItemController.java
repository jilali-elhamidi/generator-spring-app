package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PlaylistItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistItemSimpleDto;
import com.example.modules.entertainment_ecosystem.model.PlaylistItem;
import com.example.modules.entertainment_ecosystem.mapper.PlaylistItemMapper;
import com.example.modules.entertainment_ecosystem.service.PlaylistItemService;
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
 * Controller for managing PlaylistItem entities.
 */
@RestController
@RequestMapping("/api/playlistitems")
public class PlaylistItemController extends BaseController<PlaylistItem, PlaylistItemDto, PlaylistItemSimpleDto> {

    public PlaylistItemController(PlaylistItemService playlistitemService,
                                    PlaylistItemMapper playlistitemMapper) {
        super(playlistitemService, playlistitemMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PlaylistItemDto>> getAllPlaylistItems(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PlaylistItemDto>> searchPlaylistItems(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(PlaylistItem.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistItemDto> getPlaylistItemById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PlaylistItemDto> createPlaylistItem(
            @Valid @RequestBody PlaylistItemDto playlistitemDto,
            UriComponentsBuilder uriBuilder) {

        PlaylistItem entity = mapper.toEntity(playlistitemDto);
        PlaylistItem saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/playlistitems/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PlaylistItemDto>> createAllPlaylistItems(
            @Valid @RequestBody List<PlaylistItemDto> playlistitemDtoList,
            UriComponentsBuilder uriBuilder) {

        List<PlaylistItem> entities = mapper.toEntityList(playlistitemDtoList);
        List<PlaylistItem> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/playlistitems").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistItemDto> updatePlaylistItem(
            @PathVariable Long id,
            @Valid @RequestBody PlaylistItemDto playlistitemDto) {

        PlaylistItem entityToUpdate = mapper.toEntity(playlistitemDto);
        PlaylistItem updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylistItem(@PathVariable Long id) {
        return doDelete(id);
    }
}