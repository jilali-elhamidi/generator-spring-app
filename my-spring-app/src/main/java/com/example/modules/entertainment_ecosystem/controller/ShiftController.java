package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ShiftDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ShiftSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Shift;
import com.example.modules.entertainment_ecosystem.mapper.ShiftMapper;
import com.example.modules.entertainment_ecosystem.service.ShiftService;
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
 * Controller for managing Shift entities.
 */
@RestController
@RequestMapping("/api/shifts")
public class ShiftController extends BaseController<Shift, ShiftDto, ShiftSimpleDto> {

    public ShiftController(ShiftService shiftService,
                                    ShiftMapper shiftMapper) {
        super(shiftService, shiftMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ShiftDto>> getAllShifts(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ShiftDto>> searchShifts(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Shift.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDto> getShiftById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ShiftDto> createShift(
            @Valid @RequestBody ShiftDto shiftDto,
            UriComponentsBuilder uriBuilder) {

        Shift entity = mapper.toEntity(shiftDto);
        Shift saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/shifts/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ShiftDto>> createAllShifts(
            @Valid @RequestBody List<ShiftDto> shiftDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Shift> entities = mapper.toEntityList(shiftDtoList);
        List<Shift> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/shifts").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftDto> updateShift(
            @PathVariable Long id,
            @Valid @RequestBody ShiftDto shiftDto) {

        Shift entityToUpdate = mapper.toEntity(shiftDto);
        Shift updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        return doDelete(id);
    }
}