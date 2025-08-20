package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ManagerDto;
import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.mapper.ManagerMapper;
import com.example.modules.entertainment_ecosystem.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;
    private final ManagerMapper managerMapper;

    public ManagerController(ManagerService managerService,
                                    ManagerMapper managerMapper) {
        this.managerService = managerService;
        this.managerMapper = managerMapper;
    }

    @GetMapping
    public ResponseEntity<List<ManagerDto>> getAllManagers() {
        List<Manager> entities = managerService.findAll();
        return ResponseEntity.ok(managerMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDto> getManagerById(@PathVariable Long id) {
        return managerService.findById(id)
                .map(managerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ManagerDto> createManager(
            @Valid @RequestBody ManagerDto managerDto,
            UriComponentsBuilder uriBuilder) {

        Manager entity = managerMapper.toEntity(managerDto);
        Manager saved = managerService.save(entity);

        URI location = uriBuilder
                                .path("/api/managers/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(managerMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerDto> updateManager(
            @PathVariable Long id,
            @Valid @RequestBody ManagerDto managerDto) {


        Manager entityToUpdate = managerMapper.toEntity(managerDto);
        Manager updatedEntity = managerService.update(id, entityToUpdate);

        return ResponseEntity.ok(managerMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        boolean deleted = managerService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}