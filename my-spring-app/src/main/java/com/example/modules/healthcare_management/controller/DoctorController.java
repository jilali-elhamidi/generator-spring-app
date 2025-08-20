package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.DoctorDto;
import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.mapper.DoctorMapper;
import com.example.modules.healthcare_management.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    public DoctorController(DoctorService doctorService,
                                    DoctorMapper doctorMapper) {
        this.doctorService = doctorService;
        this.doctorMapper = doctorMapper;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<Doctor> entities = doctorService.findAll();
        return ResponseEntity.ok(doctorMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        return doctorService.findById(id)
                .map(doctorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(
            @Valid @RequestBody DoctorDto doctorDto,
            UriComponentsBuilder uriBuilder) {

        Doctor entity = doctorMapper.toEntity(doctorDto);
        Doctor saved = doctorService.save(entity);

        URI location = uriBuilder
                                .path("/api/doctors/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(doctorMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorDto doctorDto) {


        Doctor entityToUpdate = doctorMapper.toEntity(doctorDto);
        Doctor updatedEntity = doctorService.update(id, entityToUpdate);

        return ResponseEntity.ok(doctorMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        boolean deleted = doctorService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}