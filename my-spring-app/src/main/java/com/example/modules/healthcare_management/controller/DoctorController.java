package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.DoctorDto;
import com.example.modules.healthcare_management.dtosimple.DoctorSimpleDto;
import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.mapper.DoctorMapper;
import com.example.modules.healthcare_management.service.DoctorService;
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
 * Controller for managing Doctor entities.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController extends BaseController<Doctor, DoctorDto, DoctorSimpleDto> {

    public DoctorController(DoctorService doctorService,
                                    DoctorMapper doctorMapper) {
        super(doctorService, doctorMapper);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorDto>> getAllDoctors(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DoctorDto>> searchDoctors(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Doctor.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(
            @Valid @RequestBody DoctorDto doctorDto,
            UriComponentsBuilder uriBuilder) {

        Doctor entity = mapper.toEntity(doctorDto);
        Doctor saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/doctors/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DoctorDto>> createAllDoctors(
            @Valid @RequestBody List<DoctorDto> doctorDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Doctor> entities = mapper.toEntityList(doctorDtoList);
        List<Doctor> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/doctors").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorDto doctorDto) {

        Doctor entityToUpdate = mapper.toEntity(doctorDto);
        Doctor updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        return doDelete(id);
    }
}