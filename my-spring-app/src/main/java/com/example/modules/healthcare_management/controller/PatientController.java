package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.PatientDto;
import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.mapper.PatientMapper;
import com.example.modules.healthcare_management.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    public PatientController(PatientService patientService,
                                    PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<Patient> entities = patientService.findAll();
        return ResponseEntity.ok(patientMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        return patientService.findById(id)
                .map(patientMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(
            @Valid @RequestBody PatientDto patientDto,
            UriComponentsBuilder uriBuilder) {

        Patient entity = patientMapper.toEntity(patientDto);
        Patient saved = patientService.save(entity);

        URI location = uriBuilder
                                .path("/api/patients/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(patientMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PatientDto>> createAllPatients(
            @Valid @RequestBody List<PatientDto> patientDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Patient> entities = patientMapper.toEntityList(patientDtoList);
        List<Patient> savedEntities = patientService.saveAll(entities);

        URI location = uriBuilder.path("/api/patients").build().toUri();

        return ResponseEntity.created(location).body(patientMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientDto patientDto) {


        Patient entityToUpdate = patientMapper.toEntity(patientDto);
        Patient updatedEntity = patientService.update(id, entityToUpdate);

        return ResponseEntity.ok(patientMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        boolean deleted = patientService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}