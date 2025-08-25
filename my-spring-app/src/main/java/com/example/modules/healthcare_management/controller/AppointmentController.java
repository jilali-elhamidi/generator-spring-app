package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.AppointmentDto;
import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.mapper.AppointmentMapper;
import com.example.modules.healthcare_management.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    public AppointmentController(AppointmentService appointmentService,
                                    AppointmentMapper appointmentMapper) {
        this.appointmentService = appointmentService;
        this.appointmentMapper = appointmentMapper;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<Appointment> entities = appointmentService.findAll();
        return ResponseEntity.ok(appointmentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        return appointmentService.findById(id)
                .map(appointmentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(
            @Valid @RequestBody AppointmentDto appointmentDto,
            UriComponentsBuilder uriBuilder) {

        Appointment entity = appointmentMapper.toEntity(appointmentDto);
        Appointment saved = appointmentService.save(entity);

        URI location = uriBuilder
                                .path("/api/appointments/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(appointmentMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AppointmentDto>> createAllAppointments(
            @Valid @RequestBody List<AppointmentDto> appointmentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Appointment> entities = appointmentMapper.toEntityList(appointmentDtoList);
        List<Appointment> savedEntities = appointmentService.saveAll(entities);

        URI location = uriBuilder.path("/api/appointments").build().toUri();

        return ResponseEntity.created(location).body(appointmentMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentDto appointmentDto) {


        Appointment entityToUpdate = appointmentMapper.toEntity(appointmentDto);
        Appointment updatedEntity = appointmentService.update(id, entityToUpdate);

        return ResponseEntity.ok(appointmentMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        boolean deleted = appointmentService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}