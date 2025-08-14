package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserFollowDto;
import com.example.modules.entertainment_ecosystem.model.UserFollow;
import com.example.modules.entertainment_ecosystem.mapper.UserFollowMapper;
import com.example.modules.entertainment_ecosystem.service.UserFollowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userfollows")
public class UserFollowController {

    private final UserFollowService userfollowService;
    private final UserFollowMapper userfollowMapper;

    public UserFollowController(UserFollowService userfollowService,
                                    UserFollowMapper userfollowMapper) {
        this.userfollowService = userfollowService;
        this.userfollowMapper = userfollowMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserFollowDto>> getAllUserFollows() {
        List<UserFollow> entities = userfollowService.findAll();
        return ResponseEntity.ok(userfollowMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFollowDto> getUserFollowById(@PathVariable Long id) {
        return userfollowService.findById(id)
                .map(userfollowMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserFollowDto> createUserFollow(
            @Valid @RequestBody UserFollowDto userfollowDto,
            UriComponentsBuilder uriBuilder) {

        UserFollow entity = userfollowMapper.toEntity(userfollowDto);
        UserFollow saved = userfollowService.save(entity);
        URI location = uriBuilder.path("/api/userfollows/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(userfollowMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserFollowDto> updateUserFollow(
                @PathVariable Long id,
                @Valid @RequestBody UserFollowDto userfollowDto) {

                try {
                // Récupérer l'entité existante avec Optional
                UserFollow existing = userfollowService.findById(id)
                .orElseThrow(() -> new RuntimeException("UserFollow not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                userfollowMapper.updateEntityFromDto(userfollowDto, existing);

                // Sauvegarde
                UserFollow updatedEntity = userfollowService.save(existing);

                return ResponseEntity.ok(userfollowMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFollow(@PathVariable Long id) {
        userfollowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}