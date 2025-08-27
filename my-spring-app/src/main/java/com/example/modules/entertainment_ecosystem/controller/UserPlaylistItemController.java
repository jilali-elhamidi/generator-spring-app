package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserPlaylistItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistItemSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.mapper.UserPlaylistItemMapper;
import com.example.modules.entertainment_ecosystem.service.UserPlaylistItemService;
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
 * Controller for managing UserPlaylistItem entities.
 */
@RestController
@RequestMapping("/api/userplaylistitems")
public class UserPlaylistItemController extends BaseController<UserPlaylistItem, UserPlaylistItemDto, UserPlaylistItemSimpleDto> {

    public UserPlaylistItemController(UserPlaylistItemService userplaylistitemService,
                                    UserPlaylistItemMapper userplaylistitemMapper) {
        super(userplaylistitemService, userplaylistitemMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserPlaylistItemDto>> getAllUserPlaylistItems(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserPlaylistItemDto>> searchUserPlaylistItems(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserPlaylistItem.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPlaylistItemDto> getUserPlaylistItemById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserPlaylistItemDto> createUserPlaylistItem(
            @Valid @RequestBody UserPlaylistItemDto userplaylistitemDto,
            UriComponentsBuilder uriBuilder) {

        UserPlaylistItem entity = mapper.toEntity(userplaylistitemDto);
        UserPlaylistItem saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userplaylistitems/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserPlaylistItemDto>> createAllUserPlaylistItems(
            @Valid @RequestBody List<UserPlaylistItemDto> userplaylistitemDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserPlaylistItem> entities = mapper.toEntityList(userplaylistitemDtoList);
        List<UserPlaylistItem> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userplaylistitems").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPlaylistItemDto> updateUserPlaylistItem(
            @PathVariable Long id,
            @Valid @RequestBody UserPlaylistItemDto userplaylistitemDto) {

        UserPlaylistItem entityToUpdate = mapper.toEntity(userplaylistitemDto);
        UserPlaylistItem updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPlaylistItem(@PathVariable Long id) {
        return doDelete(id);
    }
}