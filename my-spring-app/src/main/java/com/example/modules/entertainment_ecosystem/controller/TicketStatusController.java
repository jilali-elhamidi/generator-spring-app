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
        URI location = uriBuilder.path("/api/ticketstatuss/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(ticketstatusMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<TicketStatusDto> updateTicketStatus(
                @PathVariable Long id,
                @RequestBody TicketStatusDto ticketstatusDto) {

                // Transformer le DTO en entity pour le service
                TicketStatus entityToUpdate = ticketstatusMapper.toEntity(ticketstatusDto);

                // Appel du service update
                TicketStatus updatedEntity = ticketstatusService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                TicketStatusDto updatedDto = ticketstatusMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketStatus(@PathVariable Long id) {
        ticketstatusService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}