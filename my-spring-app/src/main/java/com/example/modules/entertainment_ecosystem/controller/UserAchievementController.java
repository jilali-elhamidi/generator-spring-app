package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserAchievementDto;
import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.mapper.UserAchievementMapper;
import com.example.modules.entertainment_ecosystem.service.UserAchievementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userachievements")
public class UserAchievementController {

    private final UserAchievementService userachievementService;
    private final UserAchievementMapper userachievementMapper;

    public UserAchievementController(UserAchievementService userachievementService,
                                    UserAchievementMapper userachievementMapper) {
        this.userachievementService = userachievementService;
        this.userachievementMapper = userachievementMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserAchievementDto>> getAllUserAchievements() {
        List<UserAchievement> entities = userachievementService.findAll();
        return ResponseEntity.ok(userachievementMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAchievementDto> getUserAchievementById(@PathVariable Long id) {
        return userachievementService.findById(id)
                .map(userachievementMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserAchievementDto> createUserAchievement(
            @Valid @RequestBody UserAchievementDto userachievementDto,
            UriComponentsBuilder uriBuilder) {

        UserAchievement entity = userachievementMapper.toEntity(userachievementDto);
        UserAchievement saved = userachievementService.save(entity);
        URI location = uriBuilder.path("/api/userachievements/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(userachievementMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserAchievementDto> updateUserAchievement(
                @PathVariable Long id,
                @RequestBody UserAchievementDto userachievementDto) {

                // Transformer le DTO en entity pour le service
                UserAchievement entityToUpdate = userachievementMapper.toEntity(userachievementDto);

                // Appel du service update
                UserAchievement updatedEntity = userachievementService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserAchievementDto updatedDto = userachievementMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAchievement(@PathVariable Long id) {
        userachievementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}