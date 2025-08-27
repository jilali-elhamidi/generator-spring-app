package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.RoleDto;
import com.example.modules.social_media.dtosimple.RoleSimpleDto;
import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.mapper.RoleMapper;
import com.example.modules.social_media.service.RoleService;
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
 * Controller for managing Role entities.
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseController<Role, RoleDto, RoleSimpleDto> {

    public RoleController(RoleService roleService,
                                    RoleMapper roleMapper) {
        super(roleService, roleMapper);
    }

    @GetMapping
    public ResponseEntity<Page<RoleDto>> getAllRoles(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RoleDto>> searchRoles(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Role.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto roleDto,
            UriComponentsBuilder uriBuilder) {

        Role entity = mapper.toEntity(roleDto);
        Role saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/roles/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<RoleDto>> createAllRoles(
            @Valid @RequestBody List<RoleDto> roleDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Role> entities = mapper.toEntityList(roleDtoList);
        List<Role> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/roles").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleDto roleDto) {

        Role entityToUpdate = mapper.toEntity(roleDto);
        Role updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        return doDelete(id);
    }
}