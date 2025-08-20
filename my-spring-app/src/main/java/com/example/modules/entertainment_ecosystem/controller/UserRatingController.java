package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserRatingDto;
import com.example.modules.entertainment_ecosystem.model.UserRating;
import com.example.modules.entertainment_ecosystem.mapper.UserRatingMapper;
import com.example.modules.entertainment_ecosystem.service.UserRatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userratings")
public class UserRatingController {

    private final UserRatingService userratingService;
    private final UserRatingMapper userratingMapper;

    public UserRatingController(UserRatingService userratingService,
                                    UserRatingMapper userratingMapper) {
        this.userratingService = userratingService;
        this.userratingMapper = userratingMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserRatingDto>> getAllUserRatings() {
        List<UserRating> entities = userratingService.findAll();
        return ResponseEntity.ok(userratingMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRatingDto> getUserRatingById(@PathVariable Long id) {
        return userratingService.findById(id)
                .map(userratingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserRatingDto> createUserRating(
            @Valid @RequestBody UserRatingDto userratingDto,
            UriComponentsBuilder uriBuilder) {

        UserRating entity = userratingMapper.toEntity(userratingDto);
        UserRating saved = userratingService.save(entity);
        URI location = uriBuilder.path("/api/userratings/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(userratingMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserRatingDto> updateUserRating(
                @PathVariable Long id,
                @RequestBody UserRatingDto userratingDto) {

                // Transformer le DTO en entity pour le service
                UserRating entityToUpdate = userratingMapper.toEntity(userratingDto);

                // Appel du service update
                UserRating updatedEntity = userratingService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserRatingDto updatedDto = userratingMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteUserRating(@PathVariable Long id) {
                    boolean deleted = userratingService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}