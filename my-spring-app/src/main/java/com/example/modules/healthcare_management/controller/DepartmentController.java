package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.DepartmentDto;
import com.example.modules.healthcare_management.dtosimple.DepartmentSimpleDto;
import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.mapper.DepartmentMapper;
import com.example.modules.healthcare_management.service.DepartmentService;
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
 * Controller for managing Department entities.
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController extends BaseController<Department, DepartmentDto, DepartmentSimpleDto> {

    public DepartmentController(DepartmentService departmentService,
                                    DepartmentMapper departmentMapper) {
        super(departmentService, departmentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDto>> getAllDepartments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DepartmentDto>> searchDepartments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Department.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(
            @Valid @RequestBody DepartmentDto departmentDto,
            UriComponentsBuilder uriBuilder) {

        Department entity = mapper.toEntity(departmentDto);
        Department saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/departments/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DepartmentDto>> createAllDepartments(
            @Valid @RequestBody List<DepartmentDto> departmentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Department> entities = mapper.toEntityList(departmentDtoList);
        List<Department> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/departments").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDto departmentDto) {

        Department entityToUpdate = mapper.toEntity(departmentDto);
        Department updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        return doDelete(id);
    }
}