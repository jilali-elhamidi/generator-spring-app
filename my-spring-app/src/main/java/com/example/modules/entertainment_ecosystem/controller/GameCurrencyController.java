package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameCurrencyDto;
import com.example.modules.entertainment_ecosystem.model.GameCurrency;
import com.example.modules.entertainment_ecosystem.mapper.GameCurrencyMapper;
import com.example.modules.entertainment_ecosystem.service.GameCurrencyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gamecurrencys")
public class GameCurrencyController {

    private final GameCurrencyService gamecurrencyService;
    private final GameCurrencyMapper gamecurrencyMapper;

    public GameCurrencyController(GameCurrencyService gamecurrencyService,
                                    GameCurrencyMapper gamecurrencyMapper) {
        this.gamecurrencyService = gamecurrencyService;
        this.gamecurrencyMapper = gamecurrencyMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameCurrencyDto>> getAllGameCurrencys() {
        List<GameCurrency> entities = gamecurrencyService.findAll();
        return ResponseEntity.ok(gamecurrencyMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameCurrencyDto> getGameCurrencyById(@PathVariable Long id) {
        return gamecurrencyService.findById(id)
                .map(gamecurrencyMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameCurrencyDto> createGameCurrency(
            @Valid @RequestBody GameCurrencyDto gamecurrencyDto,
            UriComponentsBuilder uriBuilder) {

        GameCurrency entity = gamecurrencyMapper.toEntity(gamecurrencyDto);
        GameCurrency saved = gamecurrencyService.save(entity);

        URI location = uriBuilder
                                .path("/api/gamecurrencys/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(gamecurrencyMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameCurrencyDto> updateGameCurrency(
            @PathVariable Long id,
            @Valid @RequestBody GameCurrencyDto gamecurrencyDto) {


        GameCurrency entityToUpdate = gamecurrencyMapper.toEntity(gamecurrencyDto);
        GameCurrency updatedEntity = gamecurrencyService.update(id, entityToUpdate);

        return ResponseEntity.ok(gamecurrencyMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameCurrency(@PathVariable Long id) {
        boolean deleted = gamecurrencyService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}