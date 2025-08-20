package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TicketStatusDto;
import com.example.modules.entertainment_ecosystem.model.TicketStatus;
import com.example.modules.entertainment_ecosystem.mapper.TicketStatusMapper;
import com.example.modules.entertainment_ecosystem.service.TicketStatusService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ticketstatuss")
public class TicketStatusController {

    private final TicketStatusService ticketstatusService;
    private final TicketStatusMapper ticketstatusMapper;

    public TicketStatusController(TicketStatusService ticketstatusService,
                                    TicketStatusMapper ticketstatusMapper) {
        this.ticketstatusService = ticketstatusService;
        this.ticketstatusMapper = ticketstatusMapper;
    }

    @GetMapping
    public ResponseEntity<List<TicketStatusDto>> getAllTicketStatuss() {
        List<TicketStatus> entities = ticketstatusService.findAll();
        return ResponseEntity.ok(ticketstatusMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketStatusDto> getTicketStatusById(@PathVariable Long id) {
        return ticketstatusService.findById(id)
                .map(ticketstatusMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TicketStatusDto> createTicketStatus(
            @Valid @RequestBody TicketStatusDto ticketstatusDto,
            UriComponentsBuilder uriBuilder) {

        TicketStatus entity = ticketstatusMapper.toEntity(ticketstatusDto);
        TicketStatus saved = ticketstatusService.save(entity);

        URI location = uriBuilder
                                .path("/api/ticketstatuss/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(ticketstatusMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketStatusDto> updateTicketStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketStatusDto ticketstatusDto) {


        TicketStatus entityToUpdate = ticketstatusMapper.toEntity(ticketstatusDto);
        TicketStatus updatedEntity = ticketstatusService.update(id, entityToUpdate);

        return ResponseEntity.ok(ticketstatusMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketStatus(@PathVariable Long id) {
        boolean deleted = ticketstatusService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}