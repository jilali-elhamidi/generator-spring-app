package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserFollowerDto;
import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.mapper.UserFollowerMapper;
import com.example.modules.entertainment_ecosystem.service.UserFollowerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userfollowers")
public class UserFollowerController {

    private final UserFollowerService userfollowerService;
    private final UserFollowerMapper userfollowerMapper;

    public UserFollowerController(UserFollowerService userfollowerService,
                                    UserFollowerMapper userfollowerMapper) {
        this.userfollowerService = userfollowerService;
        this.userfollowerMapper = userfollowerMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserFollowerDto>> getAllUserFollowers() {
        List<UserFollower> entities = userfollowerService.findAll();
        return ResponseEntity.ok(userfollowerMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFollowerDto> getUserFollowerById(@PathVariable Long id) {
        return userfollowerService.findById(id)
                .map(userfollowerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserFollowerDto> createUserFollower(
            @Valid @RequestBody UserFollowerDto userfollowerDto,
            UriComponentsBuilder uriBuilder) {

        UserFollower entity = userfollowerMapper.toEntity(userfollowerDto);
        UserFollower saved = userfollowerService.save(entity);
        URI location = uriBuilder.path("/api/userfollowers/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(userfollowerMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserFollowerDto> updateUserFollower(
                @PathVariable Long id,
                @RequestBody UserFollowerDto userfollowerDto) {

                // Transformer le DTO en entity pour le service
                UserFollower entityToUpdate = userfollowerMapper.toEntity(userfollowerDto);

                // Appel du service update
                UserFollower updatedEntity = userfollowerService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserFollowerDto updatedDto = userfollowerMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFollower(@PathVariable Long id) {
        userfollowerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}