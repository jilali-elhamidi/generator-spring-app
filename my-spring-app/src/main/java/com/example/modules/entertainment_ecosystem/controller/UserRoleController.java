package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserRoleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserRoleSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserRole;
import com.example.modules.entertainment_ecosystem.mapper.UserRoleMapper;
import com.example.modules.entertainment_ecosystem.service.UserRoleService;
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
 * Controller for managing UserRole entities.
 */
@RestController
@RequestMapping("/api/userroles")
public class UserRoleController extends BaseController<UserRole, UserRoleDto, UserRoleSimpleDto> {

    public UserRoleController(UserRoleService userroleService,
                                    UserRoleMapper userroleMapper) {
        super(userroleService, userroleMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserRoleDto>> getAllUserRoles(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserRoleDto>> searchUserRoles(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserRole.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDto> getUserRoleById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserRoleDto> createUserRole(
            @Valid @RequestBody UserRoleDto userroleDto,
            UriComponentsBuilder uriBuilder) {

        UserRole entity = mapper.toEntity(userroleDto);
        UserRole saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userroles/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserRoleDto>> createAllUserRoles(
            @Valid @RequestBody List<UserRoleDto> userroleDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserRole> entities = mapper.toEntityList(userroleDtoList);
        List<UserRole> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userroles").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDto> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UserRoleDto userroleDto) {

        UserRole entityToUpdate = mapper.toEntity(userroleDto);
        UserRole updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        return doDelete(id);
    }
}