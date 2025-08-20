package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserBadgeDto;
import com.example.modules.entertainment_ecosystem.model.UserBadge;
import com.example.modules.entertainment_ecosystem.mapper.UserBadgeMapper;
import com.example.modules.entertainment_ecosystem.service.UserBadgeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userbadges")
public class UserBadgeController {

    private final UserBadgeService userbadgeService;
    private final UserBadgeMapper userbadgeMapper;

    public UserBadgeController(UserBadgeService userbadgeService,
                                    UserBadgeMapper userbadgeMapper) {
        this.userbadgeService = userbadgeService;
        this.userbadgeMapper = userbadgeMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserBadgeDto>> getAllUserBadges() {
        List<UserBadge> entities = userbadgeService.findAll();
        return ResponseEntity.ok(userbadgeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserBadgeDto> getUserBadgeById(@PathVariable Long id) {
        return userbadgeService.findById(id)
                .map(userbadgeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserBadgeDto> createUserBadge(
            @Valid @RequestBody UserBadgeDto userbadgeDto,
            UriComponentsBuilder uriBuilder) {

        UserBadge entity = userbadgeMapper.toEntity(userbadgeDto);
        UserBadge saved = userbadgeService.save(entity);
        URI location = uriBuilder.path("/api/userbadges/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(userbadgeMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserBadgeDto> updateUserBadge(
                @PathVariable Long id,
                @RequestBody UserBadgeDto userbadgeDto) {

                // Transformer le DTO en entity pour le service
                UserBadge entityToUpdate = userbadgeMapper.toEntity(userbadgeDto);

                // Appel du service update
                UserBadge updatedEntity = userbadgeService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserBadgeDto updatedDto = userbadgeMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteUserBadge(@PathVariable Long id) {
                    boolean deleted = userbadgeService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}