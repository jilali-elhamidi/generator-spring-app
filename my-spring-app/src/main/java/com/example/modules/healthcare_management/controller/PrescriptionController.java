package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.PrescriptionDto;
import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.mapper.PrescriptionMapper;
import com.example.modules.healthcare_management.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final PrescriptionMapper prescriptionMapper;

    public PrescriptionController(PrescriptionService prescriptionService,
                                    PrescriptionMapper prescriptionMapper) {
        this.prescriptionService = prescriptionService;
        this.prescriptionMapper = prescriptionMapper;
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDto>> getAllPrescriptions() {
        List<Prescription> entities = prescriptionService.findAll();
        return ResponseEntity.ok(prescriptionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDto> getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.findById(id)
                .map(prescriptionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(
            @Valid @RequestBody PrescriptionDto prescriptionDto,
            UriComponentsBuilder uriBuilder) {

        Prescription entity = prescriptionMapper.toEntity(prescriptionDto);
        Prescription saved = prescriptionService.save(entity);

        URI location = uriBuilder
                                .path("/api/prescriptions/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(prescriptionMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDto> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionDto prescriptionDto) {


        Prescription entityToUpdate = prescriptionMapper.toEntity(prescriptionDto);
        Prescription updatedEntity = prescriptionService.update(id, entityToUpdate);

        return ResponseEntity.ok(prescriptionMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        boolean deleted = prescriptionService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}