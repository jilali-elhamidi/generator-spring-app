package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TicketStatusDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TicketStatusSimpleDto;
import com.example.modules.entertainment_ecosystem.model.TicketStatus;
import com.example.modules.entertainment_ecosystem.mapper.TicketStatusMapper;
import com.example.modules.entertainment_ecosystem.service.TicketStatusService;
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
 * Controller for managing TicketStatus entities.
 */
@RestController
@RequestMapping("/api/ticketstatuss")
public class TicketStatusController extends BaseController<TicketStatus, TicketStatusDto, TicketStatusSimpleDto> {

    public TicketStatusController(TicketStatusService ticketstatusService,
                                    TicketStatusMapper ticketstatusMapper) {
        super(ticketstatusService, ticketstatusMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TicketStatusDto>> getAllTicketStatuss(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TicketStatusDto>> searchTicketStatuss(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(TicketStatus.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketStatusDto> getTicketStatusById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TicketStatusDto> createTicketStatus(
            @Valid @RequestBody TicketStatusDto ticketstatusDto,
            UriComponentsBuilder uriBuilder) {

        TicketStatus entity = mapper.toEntity(ticketstatusDto);
        TicketStatus saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/ticketstatuss/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TicketStatusDto>> createAllTicketStatuss(
            @Valid @RequestBody List<TicketStatusDto> ticketstatusDtoList,
            UriComponentsBuilder uriBuilder) {

        List<TicketStatus> entities = mapper.toEntityList(ticketstatusDtoList);
        List<TicketStatus> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/ticketstatuss").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketStatusDto> updateTicketStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketStatusDto ticketstatusDto) {

        TicketStatus entityToUpdate = mapper.toEntity(ticketstatusDto);
        TicketStatus updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketStatus(@PathVariable Long id) {
        return doDelete(id);
    }
}