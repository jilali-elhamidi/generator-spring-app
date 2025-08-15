package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserActivityLogDto;
import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import com.example.modules.entertainment_ecosystem.mapper.UserActivityLogMapper;
import com.example.modules.entertainment_ecosystem.service.UserActivityLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/useractivitylogs")
public class UserActivityLogController {

    private final UserActivityLogService useractivitylogService;
    private final UserActivityLogMapper useractivitylogMapper;

    public UserActivityLogController(UserActivityLogService useractivitylogService,
                                    UserActivityLogMapper useractivitylogMapper) {
        this.useractivitylogService = useractivitylogService;
        this.useractivitylogMapper = useractivitylogMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserActivityLogDto>> getAllUserActivityLogs() {
        List<UserActivityLog> entities = useractivitylogService.findAll();
        return ResponseEntity.ok(useractivitylogMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserActivityLogDto> getUserActivityLogById(@PathVariable Long id) {
        return useractivitylogService.findById(id)
                .map(useractivitylogMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserActivityLogDto> createUserActivityLog(
            @Valid @RequestBody UserActivityLogDto useractivitylogDto,
            UriComponentsBuilder uriBuilder) {

        UserActivityLog entity = useractivitylogMapper.toEntity(useractivitylogDto);
        UserActivityLog saved = useractivitylogService.save(entity);
        URI location = uriBuilder.path("/api/useractivitylogs/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(useractivitylogMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserActivityLogDto> updateUserActivityLog(
                @PathVariable Long id,
                @RequestBody UserActivityLogDto useractivitylogDto) {

                // Transformer le DTO en entity pour le service
                UserActivityLog entityToUpdate = useractivitylogMapper.toEntity(useractivitylogDto);

                // Appel du service update
                UserActivityLog updatedEntity = useractivitylogService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserActivityLogDto updatedDto = useractivitylogMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserActivityLog(@PathVariable Long id) {
        useractivitylogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}