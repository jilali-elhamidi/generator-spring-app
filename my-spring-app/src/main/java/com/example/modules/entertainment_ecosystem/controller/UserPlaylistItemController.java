package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserPlaylistItemDto;
import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.mapper.UserPlaylistItemMapper;
import com.example.modules.entertainment_ecosystem.service.UserPlaylistItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userplaylistitems")
public class UserPlaylistItemController {

    private final UserPlaylistItemService userplaylistitemService;
    private final UserPlaylistItemMapper userplaylistitemMapper;

    public UserPlaylistItemController(UserPlaylistItemService userplaylistitemService,
                                    UserPlaylistItemMapper userplaylistitemMapper) {
        this.userplaylistitemService = userplaylistitemService;
        this.userplaylistitemMapper = userplaylistitemMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserPlaylistItemDto>> getAllUserPlaylistItems() {
        List<UserPlaylistItem> entities = userplaylistitemService.findAll();
        return ResponseEntity.ok(userplaylistitemMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPlaylistItemDto> getUserPlaylistItemById(@PathVariable Long id) {
        return userplaylistitemService.findById(id)
                .map(userplaylistitemMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserPlaylistItemDto> createUserPlaylistItem(
            @Valid @RequestBody UserPlaylistItemDto userplaylistitemDto,
            UriComponentsBuilder uriBuilder) {

        UserPlaylistItem entity = userplaylistitemMapper.toEntity(userplaylistitemDto);
        UserPlaylistItem saved = userplaylistitemService.save(entity);

        URI location = uriBuilder
                                .path("/api/userplaylistitems/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userplaylistitemMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPlaylistItemDto> updateUserPlaylistItem(
            @PathVariable Long id,
            @Valid @RequestBody UserPlaylistItemDto userplaylistitemDto) {


        UserPlaylistItem entityToUpdate = userplaylistitemMapper.toEntity(userplaylistitemDto);
        UserPlaylistItem updatedEntity = userplaylistitemService.update(id, entityToUpdate);

        return ResponseEntity.ok(userplaylistitemMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPlaylistItem(@PathVariable Long id) {
        boolean deleted = userplaylistitemService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}