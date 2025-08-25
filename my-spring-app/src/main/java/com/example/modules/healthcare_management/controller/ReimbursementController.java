package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.ReimbursementDto;
import com.example.modules.healthcare_management.model.Reimbursement;
import com.example.modules.healthcare_management.mapper.ReimbursementMapper;
import com.example.modules.healthcare_management.service.ReimbursementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;
    private final ReimbursementMapper reimbursementMapper;

    public ReimbursementController(ReimbursementService reimbursementService,
                                    ReimbursementMapper reimbursementMapper) {
        this.reimbursementService = reimbursementService;
        this.reimbursementMapper = reimbursementMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReimbursementDto>> getAllReimbursements() {
        List<Reimbursement> entities = reimbursementService.findAll();
        return ResponseEntity.ok(reimbursementMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReimbursementDto> getReimbursementById(@PathVariable Long id) {
        return reimbursementService.findById(id)
                .map(reimbursementMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReimbursementDto> createReimbursement(
            @Valid @RequestBody ReimbursementDto reimbursementDto,
            UriComponentsBuilder uriBuilder) {

        Reimbursement entity = reimbursementMapper.toEntity(reimbursementDto);
        Reimbursement saved = reimbursementService.save(entity);

        URI location = uriBuilder
                                .path("/api/reimbursements/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(reimbursementMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReimbursementDto>> createAllReimbursements(
            @Valid @RequestBody List<ReimbursementDto> reimbursementDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Reimbursement> entities = reimbursementMapper.toEntityList(reimbursementDtoList);
        List<Reimbursement> savedEntities = reimbursementService.saveAll(entities);

        URI location = uriBuilder.path("/api/reimbursements").build().toUri();

        return ResponseEntity.created(location).body(reimbursementMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReimbursementDto> updateReimbursement(
            @PathVariable Long id,
            @Valid @RequestBody ReimbursementDto reimbursementDto) {


        Reimbursement entityToUpdate = reimbursementMapper.toEntity(reimbursementDto);
        Reimbursement updatedEntity = reimbursementService.update(id, entityToUpdate);

        return ResponseEntity.ok(reimbursementMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReimbursement(@PathVariable Long id) {
        boolean deleted = reimbursementService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}