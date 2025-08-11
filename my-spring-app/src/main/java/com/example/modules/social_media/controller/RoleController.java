package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.RoleDto;
import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.mapper.RoleMapper;
import com.example.modules.social_media.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

private final RoleService roleService;
private final RoleMapper roleMapper;

public RoleController(RoleService roleService, RoleMapper roleMapper) {
this.roleService = roleService;
this.roleMapper = roleMapper;
}

@GetMapping
public ResponseEntity<List<RoleDto>> getAllRoles() {
    List<Role> entities = roleService.findAll();
    return ResponseEntity.ok(roleMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        return roleService.findById(id)
        .map(roleMapper::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto roleDto,
            UriComponentsBuilder uriBuilder) {

            Role entity = roleMapper.toEntity(roleDto);
            Role saved = roleService.save(entity);
            URI location = uriBuilder.path("/api/roles/{id}")
            .buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(roleMapper.toDto(saved));
            }

            @PutMapping("/{id}")
            public ResponseEntity<RoleDto> updateRole(
                @PathVariable Long id,
                @Valid @RequestBody RoleDto roleDto) {

                try {
                Role updatedEntity = roleService.update(id, roleMapper.toEntity(roleDto));
                return ResponseEntity.ok(roleMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }

                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
                    roleService.deleteById(id);
                    return ResponseEntity.noContent().build();
                    }
                    }