package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserConnectionDto;
import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.mapper.UserConnectionMapper;
import com.example.modules.entertainment_ecosystem.service.UserConnectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userconnections")
public class UserConnectionController {

    private final UserConnectionService userconnectionService;
    private final UserConnectionMapper userconnectionMapper;

    public UserConnectionController(UserConnectionService userconnectionService,
                                    UserConnectionMapper userconnectionMapper) {
        this.userconnectionService = userconnectionService;
        this.userconnectionMapper = userconnectionMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserConnectionDto>> getAllUserConnections() {
        List<UserConnection> entities = userconnectionService.findAll();
        return ResponseEntity.ok(userconnectionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserConnectionDto> getUserConnectionById(@PathVariable Long id) {
        return userconnectionService.findById(id)
                .map(userconnectionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserConnectionDto> createUserConnection(
            @Valid @RequestBody UserConnectionDto userconnectionDto,
            UriComponentsBuilder uriBuilder) {

        UserConnection entity = userconnectionMapper.toEntity(userconnectionDto);
        UserConnection saved = userconnectionService.save(entity);

        URI location = uriBuilder
                                .path("/api/userconnections/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userconnectionMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserConnectionDto> updateUserConnection(
            @PathVariable Long id,
            @Valid @RequestBody UserConnectionDto userconnectionDto) {


        UserConnection entityToUpdate = userconnectionMapper.toEntity(userconnectionDto);
        UserConnection updatedEntity = userconnectionService.update(id, entityToUpdate);

        return ResponseEntity.ok(userconnectionMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserConnection(@PathVariable Long id) {
        boolean deleted = userconnectionService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}