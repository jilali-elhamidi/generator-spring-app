package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.DepartmentDto;
import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.mapper.DepartmentMapper;
import com.example.modules.healthcare_management.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    public DepartmentController(DepartmentService departmentService,
                                    DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<Department> entities = departmentService.findAll();
        return ResponseEntity.ok(departmentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        return departmentService.findById(id)
                .map(departmentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(
            @Valid @RequestBody DepartmentDto departmentDto,
            UriComponentsBuilder uriBuilder) {

        Department entity = departmentMapper.toEntity(departmentDto);
        Department saved = departmentService.save(entity);

        URI location = uriBuilder
                                .path("/api/departments/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(departmentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDto departmentDto) {


        Department entityToUpdate = departmentMapper.toEntity(departmentDto);
        Department updatedEntity = departmentService.update(id, entityToUpdate);

        return ResponseEntity.ok(departmentMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        boolean deleted = departmentService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}