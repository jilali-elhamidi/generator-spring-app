package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContractDto;
import com.example.modules.entertainment_ecosystem.model.Contract;
import com.example.modules.entertainment_ecosystem.mapper.ContractMapper;
import com.example.modules.entertainment_ecosystem.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;
    private final ContractMapper contractMapper;

    public ContractController(ContractService contractService,
                                    ContractMapper contractMapper) {
        this.contractService = contractService;
        this.contractMapper = contractMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContractDto>> getAllContracts() {
        List<Contract> entities = contractService.findAll();
        return ResponseEntity.ok(contractMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDto> getContractById(@PathVariable Long id) {
        return contractService.findById(id)
                .map(contractMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContractDto> createContract(
            @Valid @RequestBody ContractDto contractDto,
            UriComponentsBuilder uriBuilder) {

        Contract entity = contractMapper.toEntity(contractDto);
        Contract saved = contractService.save(entity);

        URI location = uriBuilder
                                .path("/api/contracts/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(contractMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDto> updateContract(
            @PathVariable Long id,
            @Valid @RequestBody ContractDto contractDto) {


        Contract entityToUpdate = contractMapper.toEntity(contractDto);
        Contract updatedEntity = contractService.update(id, entityToUpdate);

        return ResponseEntity.ok(contractMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        boolean deleted = contractService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}