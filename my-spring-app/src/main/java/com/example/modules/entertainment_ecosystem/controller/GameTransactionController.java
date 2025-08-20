package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameTransactionDto;
import com.example.modules.entertainment_ecosystem.model.GameTransaction;
import com.example.modules.entertainment_ecosystem.mapper.GameTransactionMapper;
import com.example.modules.entertainment_ecosystem.service.GameTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gametransactions")
public class GameTransactionController {

    private final GameTransactionService gametransactionService;
    private final GameTransactionMapper gametransactionMapper;

    public GameTransactionController(GameTransactionService gametransactionService,
                                    GameTransactionMapper gametransactionMapper) {
        this.gametransactionService = gametransactionService;
        this.gametransactionMapper = gametransactionMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameTransactionDto>> getAllGameTransactions() {
        List<GameTransaction> entities = gametransactionService.findAll();
        return ResponseEntity.ok(gametransactionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameTransactionDto> getGameTransactionById(@PathVariable Long id) {
        return gametransactionService.findById(id)
                .map(gametransactionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameTransactionDto> createGameTransaction(
            @Valid @RequestBody GameTransactionDto gametransactionDto,
            UriComponentsBuilder uriBuilder) {

        GameTransaction entity = gametransactionMapper.toEntity(gametransactionDto);
        GameTransaction saved = gametransactionService.save(entity);
        URI location = uriBuilder.path("/api/gametransactions/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gametransactionMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GameTransactionDto> updateGameTransaction(
                @PathVariable Long id,
                @RequestBody GameTransactionDto gametransactionDto) {

                // Transformer le DTO en entity pour le service
                GameTransaction entityToUpdate = gametransactionMapper.toEntity(gametransactionDto);

                // Appel du service update
                GameTransaction updatedEntity = gametransactionService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                GameTransactionDto updatedDto = gametransactionMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteGameTransaction(@PathVariable Long id) {
                    boolean deleted = gametransactionService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}