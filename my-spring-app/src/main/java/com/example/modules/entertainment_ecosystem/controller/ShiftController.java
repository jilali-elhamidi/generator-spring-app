package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ShiftDto;
import com.example.modules.entertainment_ecosystem.model.Shift;
import com.example.modules.entertainment_ecosystem.mapper.ShiftMapper;
import com.example.modules.entertainment_ecosystem.service.ShiftService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private final ShiftService shiftService;
    private final ShiftMapper shiftMapper;

    public ShiftController(ShiftService shiftService,
                                    ShiftMapper shiftMapper) {
        this.shiftService = shiftService;
        this.shiftMapper = shiftMapper;
    }

    @GetMapping
    public ResponseEntity<List<ShiftDto>> getAllShifts() {
        List<Shift> entities = shiftService.findAll();
        return ResponseEntity.ok(shiftMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDto> getShiftById(@PathVariable Long id) {
        return shiftService.findById(id)
                .map(shiftMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShiftDto> createShift(
            @Valid @RequestBody ShiftDto shiftDto,
            UriComponentsBuilder uriBuilder) {

        Shift entity = shiftMapper.toEntity(shiftDto);
        Shift saved = shiftService.save(entity);

        URI location = uriBuilder
                                .path("/api/shifts/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(shiftMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftDto> updateShift(
            @PathVariable Long id,
            @Valid @RequestBody ShiftDto shiftDto) {


        Shift entityToUpdate = shiftMapper.toEntity(shiftDto);
        Shift updatedEntity = shiftService.update(id, entityToUpdate);

        return ResponseEntity.ok(shiftMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        boolean deleted = shiftService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}