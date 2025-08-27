package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.ClientDto;
import com.example.modules.project_management.dtosimple.ClientSimpleDto;
import com.example.modules.project_management.model.Client;
import com.example.modules.project_management.mapper.ClientMapper;
import com.example.modules.project_management.service.ClientService;
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
 * Controller for managing Client entities.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController extends BaseController<Client, ClientDto, ClientSimpleDto> {

    public ClientController(ClientService clientService,
                                    ClientMapper clientMapper) {
        super(clientService, clientMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ClientDto>> getAllClients(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ClientDto>> searchClients(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Client.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(
            @Valid @RequestBody ClientDto clientDto,
            UriComponentsBuilder uriBuilder) {

        Client entity = mapper.toEntity(clientDto);
        Client saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/clients/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ClientDto>> createAllClients(
            @Valid @RequestBody List<ClientDto> clientDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Client> entities = mapper.toEntityList(clientDtoList);
        List<Client> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/clients").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientDto clientDto) {

        Client entityToUpdate = mapper.toEntity(clientDto);
        Client updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        return doDelete(id);
    }
}