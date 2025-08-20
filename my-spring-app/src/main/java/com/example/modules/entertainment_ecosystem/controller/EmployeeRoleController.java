package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EmployeeRoleDto;
import com.example.modules.entertainment_ecosystem.model.EmployeeRole;
import com.example.modules.entertainment_ecosystem.mapper.EmployeeRoleMapper;
import com.example.modules.entertainment_ecosystem.service.EmployeeRoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employeeroles")
public class EmployeeRoleController {

    private final EmployeeRoleService employeeroleService;
    private final EmployeeRoleMapper employeeroleMapper;

    public EmployeeRoleController(EmployeeRoleService employeeroleService,
                                    EmployeeRoleMapper employeeroleMapper) {
        this.employeeroleService = employeeroleService;
        this.employeeroleMapper = employeeroleMapper;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeRoleDto>> getAllEmployeeRoles() {
        List<EmployeeRole> entities = employeeroleService.findAll();
        return ResponseEntity.ok(employeeroleMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRoleDto> getEmployeeRoleById(@PathVariable Long id) {
        return employeeroleService.findById(id)
                .map(employeeroleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeRoleDto> createEmployeeRole(
            @Valid @RequestBody EmployeeRoleDto employeeroleDto,
            UriComponentsBuilder uriBuilder) {

        EmployeeRole entity = employeeroleMapper.toEntity(employeeroleDto);
        EmployeeRole saved = employeeroleService.save(entity);

        URI location = uriBuilder
                                .path("/api/employeeroles/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(employeeroleMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRoleDto> updateEmployeeRole(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRoleDto employeeroleDto) {


        EmployeeRole entityToUpdate = employeeroleMapper.toEntity(employeeroleDto);
        EmployeeRole updatedEntity = employeeroleService.update(id, entityToUpdate);

        return ResponseEntity.ok(employeeroleMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeRole(@PathVariable Long id) {
        boolean deleted = employeeroleService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}