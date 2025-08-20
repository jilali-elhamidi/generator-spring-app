package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserLevelDto;
import com.example.modules.entertainment_ecosystem.model.UserLevel;
import com.example.modules.entertainment_ecosystem.mapper.UserLevelMapper;
import com.example.modules.entertainment_ecosystem.service.UserLevelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userlevels")
public class UserLevelController {

    private final UserLevelService userlevelService;
    private final UserLevelMapper userlevelMapper;

    public UserLevelController(UserLevelService userlevelService,
                                    UserLevelMapper userlevelMapper) {
        this.userlevelService = userlevelService;
        this.userlevelMapper = userlevelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserLevelDto>> getAllUserLevels() {
        List<UserLevel> entities = userlevelService.findAll();
        return ResponseEntity.ok(userlevelMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLevelDto> getUserLevelById(@PathVariable Long id) {
        return userlevelService.findById(id)
                .map(userlevelMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserLevelDto> createUserLevel(
            @Valid @RequestBody UserLevelDto userlevelDto,
            UriComponentsBuilder uriBuilder) {

        UserLevel entity = userlevelMapper.toEntity(userlevelDto);
        UserLevel saved = userlevelService.save(entity);

        URI location = uriBuilder
                                .path("/api/userlevels/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userlevelMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserLevelDto> updateUserLevel(
            @PathVariable Long id,
            @Valid @RequestBody UserLevelDto userlevelDto) {


        UserLevel entityToUpdate = userlevelMapper.toEntity(userlevelDto);
        UserLevel updatedEntity = userlevelService.update(id, entityToUpdate);

        return ResponseEntity.ok(userlevelMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserLevel(@PathVariable Long id) {
        boolean deleted = userlevelService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}