package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameCurrencyDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameCurrencySimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameCurrency;
import com.example.modules.entertainment_ecosystem.mapper.GameCurrencyMapper;
import com.example.modules.entertainment_ecosystem.service.GameCurrencyService;
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
 * Controller for managing GameCurrency entities.
 */
@RestController
@RequestMapping("/api/gamecurrencys")
public class GameCurrencyController extends BaseController<GameCurrency, GameCurrencyDto, GameCurrencySimpleDto> {

    public GameCurrencyController(GameCurrencyService gamecurrencyService,
                                    GameCurrencyMapper gamecurrencyMapper) {
        super(gamecurrencyService, gamecurrencyMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameCurrencyDto>> getAllGameCurrencys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameCurrencyDto>> searchGameCurrencys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameCurrency.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameCurrencyDto> getGameCurrencyById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameCurrencyDto> createGameCurrency(
            @Valid @RequestBody GameCurrencyDto gamecurrencyDto,
            UriComponentsBuilder uriBuilder) {

        GameCurrency entity = mapper.toEntity(gamecurrencyDto);
        GameCurrency saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gamecurrencys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameCurrencyDto>> createAllGameCurrencys(
            @Valid @RequestBody List<GameCurrencyDto> gamecurrencyDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameCurrency> entities = mapper.toEntityList(gamecurrencyDtoList);
        List<GameCurrency> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gamecurrencys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameCurrencyDto> updateGameCurrency(
            @PathVariable Long id,
            @Valid @RequestBody GameCurrencyDto gamecurrencyDto) {

        GameCurrency entityToUpdate = mapper.toEntity(gamecurrencyDto);
        GameCurrency updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameCurrency(@PathVariable Long id) {
        return doDelete(id);
    }
}