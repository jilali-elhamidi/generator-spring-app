package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserRoleDto;
import com.example.modules.entertainment_ecosystem.model.UserRole;
import com.example.modules.entertainment_ecosystem.mapper.UserRoleMapper;
import com.example.modules.entertainment_ecosystem.service.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userroles")
public class UserRoleController {

    private final UserRoleService userroleService;
    private final UserRoleMapper userroleMapper;

    public UserRoleController(UserRoleService userroleService,
                                    UserRoleMapper userroleMapper) {
        this.userroleService = userroleService;
        this.userroleMapper = userroleMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserRoleDto>> getAllUserRoles() {
        List<UserRole> entities = userroleService.findAll();
        return ResponseEntity.ok(userroleMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDto> getUserRoleById(@PathVariable Long id) {
        return userroleService.findById(id)
                .map(userroleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserRoleDto> createUserRole(
            @Valid @RequestBody UserRoleDto userroleDto,
            UriComponentsBuilder uriBuilder) {

        UserRole entity = userroleMapper.toEntity(userroleDto);
        UserRole saved = userroleService.save(entity);

        URI location = uriBuilder
                                .path("/api/userroles/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userroleMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserRoleDto>> createAllUserRoles(
            @Valid @RequestBody List<UserRoleDto> userroleDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserRole> entities = userroleMapper.toEntityList(userroleDtoList);
        List<UserRole> savedEntities = userroleService.saveAll(entities);

        URI location = uriBuilder.path("/api/userroles").build().toUri();

        return ResponseEntity.created(location).body(userroleMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDto> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UserRoleDto userroleDto) {


        UserRole entityToUpdate = userroleMapper.toEntity(userroleDto);
        UserRole updatedEntity = userroleService.update(id, entityToUpdate);

        return ResponseEntity.ok(userroleMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        boolean deleted = userroleService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}