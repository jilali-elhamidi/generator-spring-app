package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EmployeeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.mapper.EmployeeMapper;
import com.example.modules.entertainment_ecosystem.service.EmployeeService;
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
 * Controller for managing Employee entities.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController extends BaseController<Employee, EmployeeDto, EmployeeSimpleDto> {

    public EmployeeController(EmployeeService employeeService,
                                    EmployeeMapper employeeMapper) {
        super(employeeService, employeeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDto>> getAllEmployees(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeDto>> searchEmployees(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Employee.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto,
            UriComponentsBuilder uriBuilder) {

        Employee entity = mapper.toEntity(employeeDto);
        Employee saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/employees/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EmployeeDto>> createAllEmployees(
            @Valid @RequestBody List<EmployeeDto> employeeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Employee> entities = mapper.toEntityList(employeeDtoList);
        List<Employee> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/employees").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto employeeDto) {

        Employee entityToUpdate = mapper.toEntity(employeeDto);
        Employee updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        return doDelete(id);
    }
}