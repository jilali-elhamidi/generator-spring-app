package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.MedicationDto;
import com.example.modules.healthcare_management.dtosimple.MedicationSimpleDto;
import com.example.modules.healthcare_management.model.Medication;
import com.example.modules.healthcare_management.mapper.MedicationMapper;
import com.example.modules.healthcare_management.service.MedicationService;
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
 * Controller for managing Medication entities.
 */
@RestController
@RequestMapping("/api/medications")
public class MedicationController extends BaseController<Medication, MedicationDto, MedicationSimpleDto> {

    public MedicationController(MedicationService medicationService,
                                    MedicationMapper medicationMapper) {
        super(medicationService, medicationMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MedicationDto>> getAllMedications(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MedicationDto>> searchMedications(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Medication.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationDto> getMedicationById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MedicationDto> createMedication(
            @Valid @RequestBody MedicationDto medicationDto,
            UriComponentsBuilder uriBuilder) {

        Medication entity = mapper.toEntity(medicationDto);
        Medication saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/medications/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MedicationDto>> createAllMedications(
            @Valid @RequestBody List<MedicationDto> medicationDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Medication> entities = mapper.toEntityList(medicationDtoList);
        List<Medication> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/medications").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationDto> updateMedication(
            @PathVariable Long id,
            @Valid @RequestBody MedicationDto medicationDto) {

        Medication entityToUpdate = mapper.toEntity(medicationDto);
        Medication updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        return doDelete(id);
    }
}