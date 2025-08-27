package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.ReimbursementDto;
import com.example.modules.healthcare_management.dtosimple.ReimbursementSimpleDto;
import com.example.modules.healthcare_management.model.Reimbursement;
import com.example.modules.healthcare_management.mapper.ReimbursementMapper;
import com.example.modules.healthcare_management.service.ReimbursementService;
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
 * Controller for managing Reimbursement entities.
 */
@RestController
@RequestMapping("/api/reimbursements")
public class ReimbursementController extends BaseController<Reimbursement, ReimbursementDto, ReimbursementSimpleDto> {

    public ReimbursementController(ReimbursementService reimbursementService,
                                    ReimbursementMapper reimbursementMapper) {
        super(reimbursementService, reimbursementMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ReimbursementDto>> getAllReimbursements(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReimbursementDto>> searchReimbursements(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Reimbursement.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReimbursementDto> getReimbursementById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ReimbursementDto> createReimbursement(
            @Valid @RequestBody ReimbursementDto reimbursementDto,
            UriComponentsBuilder uriBuilder) {

        Reimbursement entity = mapper.toEntity(reimbursementDto);
        Reimbursement saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/reimbursements/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReimbursementDto>> createAllReimbursements(
            @Valid @RequestBody List<ReimbursementDto> reimbursementDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Reimbursement> entities = mapper.toEntityList(reimbursementDtoList);
        List<Reimbursement> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/reimbursements").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReimbursementDto> updateReimbursement(
            @PathVariable Long id,
            @Valid @RequestBody ReimbursementDto reimbursementDto) {

        Reimbursement entityToUpdate = mapper.toEntity(reimbursementDto);
        Reimbursement updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReimbursement(@PathVariable Long id) {
        return doDelete(id);
    }
}