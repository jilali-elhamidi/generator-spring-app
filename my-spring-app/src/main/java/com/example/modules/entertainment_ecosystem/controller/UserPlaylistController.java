package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserPlaylistDto;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.mapper.UserPlaylistMapper;
import com.example.modules.entertainment_ecosystem.service.UserPlaylistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userplaylists")
public class UserPlaylistController {

    private final UserPlaylistService userplaylistService;
    private final UserPlaylistMapper userplaylistMapper;

    public UserPlaylistController(UserPlaylistService userplaylistService,
                                    UserPlaylistMapper userplaylistMapper) {
        this.userplaylistService = userplaylistService;
        this.userplaylistMapper = userplaylistMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserPlaylistDto>> getAllUserPlaylists() {
        List<UserPlaylist> entities = userplaylistService.findAll();
        return ResponseEntity.ok(userplaylistMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPlaylistDto> getUserPlaylistById(@PathVariable Long id) {
        return userplaylistService.findById(id)
                .map(userplaylistMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserPlaylistDto> createUserPlaylist(
            @Valid @RequestBody UserPlaylistDto userplaylistDto,
            UriComponentsBuilder uriBuilder) {

        UserPlaylist entity = userplaylistMapper.toEntity(userplaylistDto);
        UserPlaylist saved = userplaylistService.save(entity);
        URI location = uriBuilder.path("/api/userplaylists/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(userplaylistMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserPlaylistDto> updateUserPlaylist(
                @PathVariable Long id,
                @Valid @RequestBody UserPlaylistDto userplaylistDto) {

                try {
                // Récupérer l'entité existante avec Optional
                UserPlaylist existing = userplaylistService.findById(id)
                .orElseThrow(() -> new RuntimeException("UserPlaylist not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                userplaylistMapper.updateEntityFromDto(userplaylistDto, existing);

                // Sauvegarde
                UserPlaylist updatedEntity = userplaylistService.save(existing);

                return ResponseEntity.ok(userplaylistMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPlaylist(@PathVariable Long id) {
        userplaylistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}