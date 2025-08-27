package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContractDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContractSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Contract;
import com.example.modules.entertainment_ecosystem.mapper.ContractMapper;
import com.example.modules.entertainment_ecosystem.service.ContractService;
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
 * Controller for managing Contract entities.
 */
@RestController
@RequestMapping("/api/contracts")
public class ContractController extends BaseController<Contract, ContractDto, ContractSimpleDto> {

    public ContractController(ContractService contractService,
                                    ContractMapper contractMapper) {
        super(contractService, contractMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ContractDto>> getAllContracts(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContractDto>> searchContracts(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Contract.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDto> getContractById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ContractDto> createContract(
            @Valid @RequestBody ContractDto contractDto,
            UriComponentsBuilder uriBuilder) {

        Contract entity = mapper.toEntity(contractDto);
        Contract saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/contracts/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContractDto>> createAllContracts(
            @Valid @RequestBody List<ContractDto> contractDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Contract> entities = mapper.toEntityList(contractDtoList);
        List<Contract> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/contracts").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDto> updateContract(
            @PathVariable Long id,
            @Valid @RequestBody ContractDto contractDto) {

        Contract entityToUpdate = mapper.toEntity(contractDto);
        Contract updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        return doDelete(id);
    }
}