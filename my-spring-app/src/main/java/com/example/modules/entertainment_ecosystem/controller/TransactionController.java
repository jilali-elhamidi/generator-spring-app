package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TransactionDto;
import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.mapper.TransactionMapper;
import com.example.modules.entertainment_ecosystem.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService,
                                    TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<Transaction> entities = transactionService.findAll();
        return ResponseEntity.ok(transactionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(transactionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(
            @Valid @RequestBody TransactionDto transactionDto,
            UriComponentsBuilder uriBuilder) {

        Transaction entity = transactionMapper.toEntity(transactionDto);
        Transaction saved = transactionService.save(entity);
        URI location = uriBuilder.path("/api/transactions/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(transactionMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<TransactionDto> updateTransaction(
                @PathVariable Long id,
                @Valid @RequestBody TransactionDto transactionDto) {

                try {
                // Récupérer l'entité existante avec Optional
                Transaction existing = transactionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                transactionMapper.updateEntityFromDto(transactionDto, existing);

                // Sauvegarde
                Transaction updatedEntity = transactionService.save(existing);

                return ResponseEntity.ok(transactionMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}