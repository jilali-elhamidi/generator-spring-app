package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TransactionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TransactionSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.mapper.TransactionMapper;
import com.example.modules.entertainment_ecosystem.service.TransactionService;
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
 * Controller for managing Transaction entities.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController extends BaseController<Transaction, TransactionDto, TransactionSimpleDto> {

    public TransactionController(TransactionService transactionService,
                                    TransactionMapper transactionMapper) {
        super(transactionService, transactionMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDto>> getAllTransactions(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TransactionDto>> searchTransactions(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Transaction.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(
            @Valid @RequestBody TransactionDto transactionDto,
            UriComponentsBuilder uriBuilder) {

        Transaction entity = mapper.toEntity(transactionDto);
        Transaction saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/transactions/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TransactionDto>> createAllTransactions(
            @Valid @RequestBody List<TransactionDto> transactionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Transaction> entities = mapper.toEntityList(transactionDtoList);
        List<Transaction> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/transactions").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionDto transactionDto) {

        Transaction entityToUpdate = mapper.toEntity(transactionDto);
        Transaction updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        return doDelete(id);
    }
}