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
        URI location = uriBuilder.path("/api/contracts/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(contractMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ContractDto> updateContract(
                @PathVariable Long id,
                @RequestBody ContractDto contractDto) {

                // Transformer le DTO en entity pour le service
                Contract entityToUpdate = contractMapper.toEntity(contractDto);

                // Appel du service update
                Contract updatedEntity = contractService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ContractDto updatedDto = contractMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}