package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserConnectionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserConnectionSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserConnection;
import com.example.modules.entertainment_ecosystem.mapper.UserConnectionMapper;
import com.example.modules.entertainment_ecosystem.service.UserConnectionService;
import com.example.core.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing UserConnection entities.
 */
@RestController
@RequestMapping("/api/userconnections")
public class UserConnectionController extends BaseController<UserConnection, UserConnectionDto, UserConnectionSimpleDto> {

    public UserConnectionController(UserConnectionService userconnectionService,
                                    UserConnectionMapper userconnectionMapper) {
        super(userconnectionService, userconnectionMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserConnectionDto>> getAllUserConnections(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserConnectionDto>> searchUserConnections(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserConnection.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserConnectionDto> getUserConnectionById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserConnectionDto> createUserConnection(
            @Valid @RequestBody UserConnectionDto userconnectionDto,
            UriComponentsBuilder uriBuilder) {

        UserConnection entity = mapper.toEntity(userconnectionDto);
        UserConnection saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userconnections/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserConnectionDto>> createAllUserConnections(
            @Valid @RequestBody List<UserConnectionDto> userconnectionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserConnection> entities = mapper.toEntityList(userconnectionDtoList);
        List<UserConnection> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userconnections").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserConnectionDto> updateUserConnection(
            @PathVariable Long id,
            @Valid @RequestBody UserConnectionDto userconnectionDto) {

        UserConnection entityToUpdate = mapper.toEntity(userconnectionDto);
        UserConnection updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserConnection(@PathVariable Long id) {
        return doDelete(id);
    }
}