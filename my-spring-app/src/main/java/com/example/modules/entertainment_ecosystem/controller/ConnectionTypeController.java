package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ConnectionTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ConnectionTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ConnectionType;
import com.example.modules.entertainment_ecosystem.mapper.ConnectionTypeMapper;
import com.example.modules.entertainment_ecosystem.service.ConnectionTypeService;
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
 * Controller for managing ConnectionType entities.
 */
@RestController
@RequestMapping("/api/connectiontypes")
public class ConnectionTypeController extends BaseController<ConnectionType, ConnectionTypeDto, ConnectionTypeSimpleDto> {

    public ConnectionTypeController(ConnectionTypeService connectiontypeService,
                                    ConnectionTypeMapper connectiontypeMapper) {
        super(connectiontypeService, connectiontypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ConnectionTypeDto>> getAllConnectionTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ConnectionTypeDto>> searchConnectionTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ConnectionType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionTypeDto> getConnectionTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ConnectionTypeDto> createConnectionType(
            @Valid @RequestBody ConnectionTypeDto connectiontypeDto,
            UriComponentsBuilder uriBuilder) {

        ConnectionType entity = mapper.toEntity(connectiontypeDto);
        ConnectionType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/connectiontypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ConnectionTypeDto>> createAllConnectionTypes(
            @Valid @RequestBody List<ConnectionTypeDto> connectiontypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ConnectionType> entities = mapper.toEntityList(connectiontypeDtoList);
        List<ConnectionType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/connectiontypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConnectionTypeDto> updateConnectionType(
            @PathVariable Long id,
            @Valid @RequestBody ConnectionTypeDto connectiontypeDto) {

        ConnectionType entityToUpdate = mapper.toEntity(connectiontypeDto);
        ConnectionType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnectionType(@PathVariable Long id) {
        return doDelete(id);
    }
}