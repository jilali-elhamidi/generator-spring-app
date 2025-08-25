package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.MedicalRecordDto;
import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.mapper.MedicalRecordMapper;
import com.example.modules.healthcare_management.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medicalrecords")
public class MedicalRecordController {

    private final MedicalRecordService medicalrecordService;
    private final MedicalRecordMapper medicalrecordMapper;

    public MedicalRecordController(MedicalRecordService medicalrecordService,
                                    MedicalRecordMapper medicalrecordMapper) {
        this.medicalrecordService = medicalrecordService;
        this.medicalrecordMapper = medicalrecordMapper;
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordDto>> getAllMedicalRecords() {
        List<MedicalRecord> entities = medicalrecordService.findAll();
        return ResponseEntity.ok(medicalrecordMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDto> getMedicalRecordById(@PathVariable Long id) {
        return medicalrecordService.findById(id)
                .map(medicalrecordMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(
            @Valid @RequestBody MedicalRecordDto medicalrecordDto,
            UriComponentsBuilder uriBuilder) {

        MedicalRecord entity = medicalrecordMapper.toEntity(medicalrecordDto);
        MedicalRecord saved = medicalrecordService.save(entity);

        URI location = uriBuilder
                                .path("/api/medicalrecords/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(medicalrecordMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MedicalRecordDto>> createAllMedicalRecords(
            @Valid @RequestBody List<MedicalRecordDto> medicalrecordDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MedicalRecord> entities = medicalrecordMapper.toEntityList(medicalrecordDtoList);
        List<MedicalRecord> savedEntities = medicalrecordService.saveAll(entities);

        URI location = uriBuilder.path("/api/medicalrecords").build().toUri();

        return ResponseEntity.created(location).body(medicalrecordMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordDto medicalrecordDto) {


        MedicalRecord entityToUpdate = medicalrecordMapper.toEntity(medicalrecordDto);
        MedicalRecord updatedEntity = medicalrecordService.update(id, entityToUpdate);

        return ResponseEntity.ok(medicalrecordMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        boolean deleted = medicalrecordService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}