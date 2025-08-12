package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.MedicationDto;
import com.example.modules.healthcare_management.model.Medication;
import com.example.modules.healthcare_management.mapper.MedicationMapper;
import com.example.modules.healthcare_management.service.MedicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationService medicationService;
    private final MedicationMapper medicationMapper;

    public MedicationController(MedicationService medicationService,
                                    MedicationMapper medicationMapper) {
        this.medicationService = medicationService;
        this.medicationMapper = medicationMapper;
    }

    @GetMapping
    public ResponseEntity<List<MedicationDto>> getAllMedications() {
        List<Medication> entities = medicationService.findAll();
        return ResponseEntity.ok(medicationMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationDto> getMedicationById(@PathVariable Long id) {
        return medicationService.findById(id)
                .map(medicationMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MedicationDto> createMedication(
            @Valid @RequestBody MedicationDto medicationDto,
            UriComponentsBuilder uriBuilder) {

        Medication entity = medicationMapper.toEntity(medicationDto);
        Medication saved = medicationService.save(entity);
        URI location = uriBuilder.path("/api/medications/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(medicationMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationDto> updateMedication(
            @PathVariable Long id,
            @Valid @RequestBody MedicationDto medicationDto) {

        try {
            Medication updatedEntity = medicationService.update(
                    id,
                    medicationMapper.toEntity(medicationDto)
            );
            return ResponseEntity.ok(medicationMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        medicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}