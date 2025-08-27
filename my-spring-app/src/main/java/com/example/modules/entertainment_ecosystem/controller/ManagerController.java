package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ManagerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ManagerSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.mapper.ManagerMapper;
import com.example.modules.entertainment_ecosystem.service.ManagerService;
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
 * Controller for managing Manager entities.
 */
@RestController
@RequestMapping("/api/managers")
public class ManagerController extends BaseController<Manager, ManagerDto, ManagerSimpleDto> {

    public ManagerController(ManagerService managerService,
                                    ManagerMapper managerMapper) {
        super(managerService, managerMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ManagerDto>> getAllManagers(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ManagerDto>> searchManagers(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Manager.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDto> getManagerById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ManagerDto> createManager(
            @Valid @RequestBody ManagerDto managerDto,
            UriComponentsBuilder uriBuilder) {

        Manager entity = mapper.toEntity(managerDto);
        Manager saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/managers/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ManagerDto>> createAllManagers(
            @Valid @RequestBody List<ManagerDto> managerDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Manager> entities = mapper.toEntityList(managerDtoList);
        List<Manager> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/managers").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerDto> updateManager(
            @PathVariable Long id,
            @Valid @RequestBody ManagerDto managerDto) {

        Manager entityToUpdate = mapper.toEntity(managerDto);
        Manager updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        return doDelete(id);
    }
}