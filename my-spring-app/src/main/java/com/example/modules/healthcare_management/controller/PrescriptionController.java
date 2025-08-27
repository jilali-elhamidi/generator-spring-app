package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.PrescriptionDto;
import com.example.modules.healthcare_management.dtosimple.PrescriptionSimpleDto;
import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.mapper.PrescriptionMapper;
import com.example.modules.healthcare_management.service.PrescriptionService;
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
 * Controller for managing Prescription entities.
 */
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController extends BaseController<Prescription, PrescriptionDto, PrescriptionSimpleDto> {

    public PrescriptionController(PrescriptionService prescriptionService,
                                    PrescriptionMapper prescriptionMapper) {
        super(prescriptionService, prescriptionMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PrescriptionDto>> getAllPrescriptions(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PrescriptionDto>> searchPrescriptions(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Prescription.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDto> getPrescriptionById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(
            @Valid @RequestBody PrescriptionDto prescriptionDto,
            UriComponentsBuilder uriBuilder) {

        Prescription entity = mapper.toEntity(prescriptionDto);
        Prescription saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/prescriptions/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PrescriptionDto>> createAllPrescriptions(
            @Valid @RequestBody List<PrescriptionDto> prescriptionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Prescription> entities = mapper.toEntityList(prescriptionDtoList);
        List<Prescription> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/prescriptions").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDto> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionDto prescriptionDto) {

        Prescription entityToUpdate = mapper.toEntity(prescriptionDto);
        Prescription updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        return doDelete(id);
    }
}