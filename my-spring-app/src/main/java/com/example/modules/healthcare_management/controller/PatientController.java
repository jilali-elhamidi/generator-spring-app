package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.PatientDto;
import com.example.modules.healthcare_management.dtosimple.PatientSimpleDto;
import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.mapper.PatientMapper;
import com.example.modules.healthcare_management.service.PatientService;
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
 * Controller for managing Patient entities.
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController extends BaseController<Patient, PatientDto, PatientSimpleDto> {

    public PatientController(PatientService patientService,
                                    PatientMapper patientMapper) {
        super(patientService, patientMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PatientDto>> getAllPatients(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PatientDto>> searchPatients(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Patient.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(
            @Valid @RequestBody PatientDto patientDto,
            UriComponentsBuilder uriBuilder) {

        Patient entity = mapper.toEntity(patientDto);
        Patient saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/patients/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PatientDto>> createAllPatients(
            @Valid @RequestBody List<PatientDto> patientDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Patient> entities = mapper.toEntityList(patientDtoList);
        List<Patient> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/patients").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientDto patientDto) {

        Patient entityToUpdate = mapper.toEntity(patientDto);
        Patient updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        return doDelete(id);
    }
}