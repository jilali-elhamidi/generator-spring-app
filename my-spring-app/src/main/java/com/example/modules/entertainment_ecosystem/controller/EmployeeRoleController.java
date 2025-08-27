package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EmployeeRoleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeRoleSimpleDto;
import com.example.modules.entertainment_ecosystem.model.EmployeeRole;
import com.example.modules.entertainment_ecosystem.mapper.EmployeeRoleMapper;
import com.example.modules.entertainment_ecosystem.service.EmployeeRoleService;
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
 * Controller for managing EmployeeRole entities.
 */
@RestController
@RequestMapping("/api/employeeroles")
public class EmployeeRoleController extends BaseController<EmployeeRole, EmployeeRoleDto, EmployeeRoleSimpleDto> {

    public EmployeeRoleController(EmployeeRoleService employeeroleService,
                                    EmployeeRoleMapper employeeroleMapper) {
        super(employeeroleService, employeeroleMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeRoleDto>> getAllEmployeeRoles(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeRoleDto>> searchEmployeeRoles(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(EmployeeRole.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRoleDto> getEmployeeRoleById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeRoleDto> createEmployeeRole(
            @Valid @RequestBody EmployeeRoleDto employeeroleDto,
            UriComponentsBuilder uriBuilder) {

        EmployeeRole entity = mapper.toEntity(employeeroleDto);
        EmployeeRole saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/employeeroles/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EmployeeRoleDto>> createAllEmployeeRoles(
            @Valid @RequestBody List<EmployeeRoleDto> employeeroleDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EmployeeRole> entities = mapper.toEntityList(employeeroleDtoList);
        List<EmployeeRole> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/employeeroles").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRoleDto> updateEmployeeRole(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRoleDto employeeroleDto) {

        EmployeeRole entityToUpdate = mapper.toEntity(employeeroleDto);
        EmployeeRole updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeRole(@PathVariable Long id) {
        return doDelete(id);
    }
}