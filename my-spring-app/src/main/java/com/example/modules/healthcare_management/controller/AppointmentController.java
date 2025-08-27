package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.AppointmentDto;
import com.example.modules.healthcare_management.dtosimple.AppointmentSimpleDto;
import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.mapper.AppointmentMapper;
import com.example.modules.healthcare_management.service.AppointmentService;
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
 * Controller for managing Appointment entities.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController extends BaseController<Appointment, AppointmentDto, AppointmentSimpleDto> {

    public AppointmentController(AppointmentService appointmentService,
                                    AppointmentMapper appointmentMapper) {
        super(appointmentService, appointmentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentDto>> getAllAppointments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AppointmentDto>> searchAppointments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Appointment.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(
            @Valid @RequestBody AppointmentDto appointmentDto,
            UriComponentsBuilder uriBuilder) {

        Appointment entity = mapper.toEntity(appointmentDto);
        Appointment saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/appointments/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AppointmentDto>> createAllAppointments(
            @Valid @RequestBody List<AppointmentDto> appointmentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Appointment> entities = mapper.toEntityList(appointmentDtoList);
        List<Appointment> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/appointments").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentDto appointmentDto) {

        Appointment entityToUpdate = mapper.toEntity(appointmentDto);
        Appointment updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        return doDelete(id);
    }
}