package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EmployeeDto;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.mapper.EmployeeMapper;
import com.example.modules.entertainment_ecosystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService,
                                    EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<Employee> entities = employeeService.findAll();
        return ResponseEntity.ok(employeeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(employeeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto,
            UriComponentsBuilder uriBuilder) {

        Employee entity = employeeMapper.toEntity(employeeDto);
        Employee saved = employeeService.save(entity);

        URI location = uriBuilder
                                .path("/api/employees/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(employeeMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EmployeeDto>> createAllEmployees(
            @Valid @RequestBody List<EmployeeDto> employeeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Employee> entities = employeeMapper.toEntityList(employeeDtoList);
        List<Employee> savedEntities = employeeService.saveAll(entities);

        URI location = uriBuilder.path("/api/employees").build().toUri();

        return ResponseEntity.created(location).body(employeeMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto employeeDto) {


        Employee entityToUpdate = employeeMapper.toEntity(employeeDto);
        Employee updatedEntity = employeeService.update(id, entityToUpdate);

        return ResponseEntity.ok(employeeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}