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
                @RequestBody UserFollowDto userfollowDto) {

                // Transformer le DTO en entity pour le service
                UserFollow entityToUpdate = userfollowMapper.toEntity(userfollowDto);

                // Appel du service update
                UserFollow updatedEntity = userfollowService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserFollowDto updatedDto = userfollowMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFollow(@PathVariable Long id) {
        userfollowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}