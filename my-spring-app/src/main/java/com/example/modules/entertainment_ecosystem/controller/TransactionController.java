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
                @RequestBody TransactionDto transactionDto) {

                // Transformer le DTO en entity pour le service
                Transaction entityToUpdate = transactionMapper.toEntity(transactionDto);

                // Appel du service update
                Transaction updatedEntity = transactionService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                TransactionDto updatedDto = transactionMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
                    boolean deleted = transactionService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}