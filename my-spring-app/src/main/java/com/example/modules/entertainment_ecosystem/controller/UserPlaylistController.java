package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserPlaylistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.mapper.UserPlaylistMapper;
import com.example.modules.entertainment_ecosystem.service.UserPlaylistService;
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
 * Controller for managing UserPlaylist entities.
 */
@RestController
@RequestMapping("/api/userplaylists")
public class UserPlaylistController extends BaseController<UserPlaylist, UserPlaylistDto, UserPlaylistSimpleDto> {

    public UserPlaylistController(UserPlaylistService userplaylistService,
                                    UserPlaylistMapper userplaylistMapper) {
        super(userplaylistService, userplaylistMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserPlaylistDto>> getAllUserPlaylists(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserPlaylistDto>> searchUserPlaylists(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserPlaylist.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPlaylistDto> getUserPlaylistById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserPlaylistDto> createUserPlaylist(
            @Valid @RequestBody UserPlaylistDto userplaylistDto,
            UriComponentsBuilder uriBuilder) {

        UserPlaylist entity = mapper.toEntity(userplaylistDto);
        UserPlaylist saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userplaylists/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserPlaylistDto>> createAllUserPlaylists(
            @Valid @RequestBody List<UserPlaylistDto> userplaylistDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserPlaylist> entities = mapper.toEntityList(userplaylistDtoList);
        List<UserPlaylist> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userplaylists").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPlaylistDto> updateUserPlaylist(
            @PathVariable Long id,
            @Valid @RequestBody UserPlaylistDto userplaylistDto) {

        UserPlaylist entityToUpdate = mapper.toEntity(userplaylistDto);
        UserPlaylist updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPlaylist(@PathVariable Long id) {
        return doDelete(id);
    }
}