package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TicketDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TicketSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.mapper.TicketMapper;
import com.example.modules.entertainment_ecosystem.service.TicketService;
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
 * Controller for managing Ticket entities.
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController extends BaseController<Ticket, TicketDto, TicketSimpleDto> {

    public TicketController(TicketService ticketService,
                                    TicketMapper ticketMapper) {
        super(ticketService, ticketMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TicketDto>> getAllTickets(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TicketDto>> searchTickets(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Ticket.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TicketDto> createTicket(
            @Valid @RequestBody TicketDto ticketDto,
            UriComponentsBuilder uriBuilder) {

        Ticket entity = mapper.toEntity(ticketDto);
        Ticket saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/tickets/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TicketDto>> createAllTickets(
            @Valid @RequestBody List<TicketDto> ticketDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Ticket> entities = mapper.toEntityList(ticketDtoList);
        List<Ticket> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/tickets").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketDto ticketDto) {

        Ticket entityToUpdate = mapper.toEntity(ticketDto);
        Ticket updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        return doDelete(id);
    }
}