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
        URI location = uriBuilder.path("/api/appointments/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(appointmentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentDto appointmentDto) {

        try {
            Appointment updatedEntity = appointmentService.update(
                    id,
                    appointmentMapper.toEntity(appointmentDto)
            );
            return ResponseEntity.ok(appointmentMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}