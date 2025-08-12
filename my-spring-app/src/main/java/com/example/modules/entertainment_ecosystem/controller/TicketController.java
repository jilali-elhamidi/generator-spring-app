package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TicketDto;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.mapper.TicketMapper;
import com.example.modules.entertainment_ecosystem.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public TicketController(TicketService ticketService,
                                    TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<Ticket> entities = ticketService.findAll();
        return ResponseEntity.ok(ticketMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ticketMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TicketDto> createTicket(
            @Valid @RequestBody TicketDto ticketDto,
            UriComponentsBuilder uriBuilder) {

        Ticket entity = ticketMapper.toEntity(ticketDto);
        Ticket saved = ticketService.save(entity);
        URI location = uriBuilder.path("/api/tickets/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(ticketMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketDto ticketDto) {

        try {
            Ticket updatedEntity = ticketService.update(
                    id,
                    ticketMapper.toEntity(ticketDto)
            );
            return ResponseEntity.ok(ticketMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}