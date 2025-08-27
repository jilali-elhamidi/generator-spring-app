package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameTransactionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameTransactionSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameTransaction;
import com.example.modules.entertainment_ecosystem.mapper.GameTransactionMapper;
import com.example.modules.entertainment_ecosystem.service.GameTransactionService;
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
 * Controller for managing GameTransaction entities.
 */
@RestController
@RequestMapping("/api/gametransactions")
public class GameTransactionController extends BaseController<GameTransaction, GameTransactionDto, GameTransactionSimpleDto> {

    public GameTransactionController(GameTransactionService gametransactionService,
                                    GameTransactionMapper gametransactionMapper) {
        super(gametransactionService, gametransactionMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameTransactionDto>> getAllGameTransactions(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameTransactionDto>> searchGameTransactions(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameTransaction.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameTransactionDto> getGameTransactionById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameTransactionDto> createGameTransaction(
            @Valid @RequestBody GameTransactionDto gametransactionDto,
            UriComponentsBuilder uriBuilder) {

        GameTransaction entity = mapper.toEntity(gametransactionDto);
        GameTransaction saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gametransactions/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameTransactionDto>> createAllGameTransactions(
            @Valid @RequestBody List<GameTransactionDto> gametransactionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameTransaction> entities = mapper.toEntityList(gametransactionDtoList);
        List<GameTransaction> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gametransactions").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameTransactionDto> updateGameTransaction(
            @PathVariable Long id,
            @Valid @RequestBody GameTransactionDto gametransactionDto) {

        GameTransaction entityToUpdate = mapper.toEntity(gametransactionDto);
        GameTransaction updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameTransaction(@PathVariable Long id) {
        return doDelete(id);
    }
}