package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.MedicalRecordDto;
import com.example.modules.healthcare_management.dtosimple.MedicalRecordSimpleDto;
import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.mapper.MedicalRecordMapper;
import com.example.modules.healthcare_management.service.MedicalRecordService;
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
 * Controller for managing MedicalRecord entities.
 */
@RestController
@RequestMapping("/api/medicalrecords")
public class MedicalRecordController extends BaseController<MedicalRecord, MedicalRecordDto, MedicalRecordSimpleDto> {

    public MedicalRecordController(MedicalRecordService medicalrecordService,
                                    MedicalRecordMapper medicalrecordMapper) {
        super(medicalrecordService, medicalrecordMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MedicalRecordDto>> getAllMedicalRecords(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MedicalRecordDto>> searchMedicalRecords(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MedicalRecord.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDto> getMedicalRecordById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(
            @Valid @RequestBody MedicalRecordDto medicalrecordDto,
            UriComponentsBuilder uriBuilder) {

        MedicalRecord entity = mapper.toEntity(medicalrecordDto);
        MedicalRecord saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/medicalrecords/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MedicalRecordDto>> createAllMedicalRecords(
            @Valid @RequestBody List<MedicalRecordDto> medicalrecordDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MedicalRecord> entities = mapper.toEntityList(medicalrecordDtoList);
        List<MedicalRecord> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/medicalrecords").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordDto medicalrecordDto) {

        MedicalRecord entityToUpdate = mapper.toEntity(medicalrecordDto);
        MedicalRecord updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        return doDelete(id);
    }
}