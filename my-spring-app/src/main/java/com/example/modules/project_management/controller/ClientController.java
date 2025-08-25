package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.ClientDto;
import com.example.modules.project_management.model.Client;
import com.example.modules.project_management.mapper.ClientMapper;
import com.example.modules.project_management.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService,
                                    ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<Client> entities = clientService.findAll();
        return ResponseEntity.ok(clientMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(clientMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(
            @Valid @RequestBody ClientDto clientDto,
            UriComponentsBuilder uriBuilder) {

        Client entity = clientMapper.toEntity(clientDto);
        Client saved = clientService.save(entity);

        URI location = uriBuilder
                                .path("/api/clients/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(clientMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ClientDto>> createAllClients(
            @Valid @RequestBody List<ClientDto> clientDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Client> entities = clientMapper.toEntityList(clientDtoList);
        List<Client> savedEntities = clientService.saveAll(entities);

        URI location = uriBuilder.path("/api/clients").build().toUri();

        return ResponseEntity.created(location).body(clientMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientDto clientDto) {


        Client entityToUpdate = clientMapper.toEntity(clientDto);
        Client updatedEntity = clientService.update(id, entityToUpdate);

        return ResponseEntity.ok(clientMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        boolean deleted = clientService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}