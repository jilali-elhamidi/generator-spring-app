package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.MedicalFileDto;
import com.example.modules.healthcare_management.dtosimple.MedicalFileSimpleDto;
import com.example.modules.healthcare_management.model.MedicalFile;
import com.example.modules.healthcare_management.mapper.MedicalFileMapper;
import com.example.modules.healthcare_management.service.MedicalFileService;
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
 * Controller for managing MedicalFile entities.
 */
@RestController
@RequestMapping("/api/medicalfiles")
public class MedicalFileController extends BaseController<MedicalFile, MedicalFileDto, MedicalFileSimpleDto> {

    public MedicalFileController(MedicalFileService medicalfileService,
                                    MedicalFileMapper medicalfileMapper) {
        super(medicalfileService, medicalfileMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MedicalFileDto>> getAllMedicalFiles(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MedicalFileDto>> searchMedicalFiles(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MedicalFile.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalFileDto> getMedicalFileById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MedicalFileDto> createMedicalFile(
            @Valid @RequestBody MedicalFileDto medicalfileDto,
            UriComponentsBuilder uriBuilder) {

        MedicalFile entity = mapper.toEntity(medicalfileDto);
        MedicalFile saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/medicalfiles/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MedicalFileDto>> createAllMedicalFiles(
            @Valid @RequestBody List<MedicalFileDto> medicalfileDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MedicalFile> entities = mapper.toEntityList(medicalfileDtoList);
        List<MedicalFile> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/medicalfiles").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalFileDto> updateMedicalFile(
            @PathVariable Long id,
            @Valid @RequestBody MedicalFileDto medicalfileDto) {

        MedicalFile entityToUpdate = mapper.toEntity(medicalfileDto);
        MedicalFile updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalFile(@PathVariable Long id) {
        return doDelete(id);
    }
}