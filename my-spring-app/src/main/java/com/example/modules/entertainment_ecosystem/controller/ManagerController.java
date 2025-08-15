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
        URI location = uriBuilder.path("/api/managers/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(managerMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ManagerDto> updateManager(
                @PathVariable Long id,
                @RequestBody ManagerDto managerDto) {

                // Transformer le DTO en entity pour le service
                Manager entityToUpdate = managerMapper.toEntity(managerDto);

                // Appel du service update
                Manager updatedEntity = managerService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ManagerDto updatedDto = managerMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
                    boolean deleted = managerService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}