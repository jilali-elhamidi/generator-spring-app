package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ConnectionTypeDto;
import com.example.modules.entertainment_ecosystem.model.ConnectionType;
import com.example.modules.entertainment_ecosystem.mapper.ConnectionTypeMapper;
import com.example.modules.entertainment_ecosystem.service.ConnectionTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/connectiontypes")
public class ConnectionTypeController {

    private final ConnectionTypeService connectiontypeService;
    private final ConnectionTypeMapper connectiontypeMapper;

    public ConnectionTypeController(ConnectionTypeService connectiontypeService,
                                    ConnectionTypeMapper connectiontypeMapper) {
        this.connectiontypeService = connectiontypeService;
        this.connectiontypeMapper = connectiontypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<ConnectionTypeDto>> getAllConnectionTypes() {
        List<ConnectionType> entities = connectiontypeService.findAll();
        return ResponseEntity.ok(connectiontypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionTypeDto> getConnectionTypeById(@PathVariable Long id) {
        return connectiontypeService.findById(id)
                .map(connectiontypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConnectionTypeDto> createConnectionType(
            @Valid @RequestBody ConnectionTypeDto connectiontypeDto,
            UriComponentsBuilder uriBuilder) {

        ConnectionType entity = connectiontypeMapper.toEntity(connectiontypeDto);
        ConnectionType saved = connectiontypeService.save(entity);

        URI location = uriBuilder
                                .path("/api/connectiontypes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(connectiontypeMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConnectionTypeDto> updateConnectionType(
            @PathVariable Long id,
            @Valid @RequestBody ConnectionTypeDto connectiontypeDto) {


        ConnectionType entityToUpdate = connectiontypeMapper.toEntity(connectiontypeDto);
        ConnectionType updatedEntity = connectiontypeService.update(id, entityToUpdate);

        return ResponseEntity.ok(connectiontypeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnectionType(@PathVariable Long id) {
        boolean deleted = connectiontypeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}