package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.MedicalFileDto;
import com.example.modules.healthcare_management.model.MedicalFile;
import com.example.modules.healthcare_management.mapper.MedicalFileMapper;
import com.example.modules.healthcare_management.service.MedicalFileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medicalfiles")
public class MedicalFileController {

    private final MedicalFileService medicalfileService;
    private final MedicalFileMapper medicalfileMapper;

    public MedicalFileController(MedicalFileService medicalfileService,
                                    MedicalFileMapper medicalfileMapper) {
        this.medicalfileService = medicalfileService;
        this.medicalfileMapper = medicalfileMapper;
    }

    @GetMapping
    public ResponseEntity<List<MedicalFileDto>> getAllMedicalFiles() {
        List<MedicalFile> entities = medicalfileService.findAll();
        return ResponseEntity.ok(medicalfileMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalFileDto> getMedicalFileById(@PathVariable Long id) {
        return medicalfileService.findById(id)
                .map(medicalfileMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MedicalFileDto> createMedicalFile(
            @Valid @RequestBody MedicalFileDto medicalfileDto,
            UriComponentsBuilder uriBuilder) {

        MedicalFile entity = medicalfileMapper.toEntity(medicalfileDto);
        MedicalFile saved = medicalfileService.save(entity);
        URI location = uriBuilder.path("/api/medicalfiles/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(medicalfileMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalFileDto> updateMedicalFile(
            @PathVariable Long id,
            @Valid @RequestBody MedicalFileDto medicalfileDto) {

        try {
            MedicalFile updatedEntity = medicalfileService.update(
                    id,
                    medicalfileMapper.toEntity(medicalfileDto)
            );
            return ResponseEntity.ok(medicalfileMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalFile(@PathVariable Long id) {
        medicalfileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}