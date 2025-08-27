package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SeasonDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SeasonSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.mapper.SeasonMapper;
import com.example.modules.entertainment_ecosystem.service.SeasonService;
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
 * Controller for managing Season entities.
 */
@RestController
@RequestMapping("/api/seasons")
public class SeasonController extends BaseController<Season, SeasonDto, SeasonSimpleDto> {

    public SeasonController(SeasonService seasonService,
                                    SeasonMapper seasonMapper) {
        super(seasonService, seasonMapper);
    }

    @GetMapping
    public ResponseEntity<Page<SeasonDto>> getAllSeasons(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SeasonDto>> searchSeasons(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Season.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonDto> getSeasonById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<SeasonDto> createSeason(
            @Valid @RequestBody SeasonDto seasonDto,
            UriComponentsBuilder uriBuilder) {

        Season entity = mapper.toEntity(seasonDto);
        Season saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/seasons/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SeasonDto>> createAllSeasons(
            @Valid @RequestBody List<SeasonDto> seasonDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Season> entities = mapper.toEntityList(seasonDtoList);
        List<Season> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/seasons").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeasonDto> updateSeason(
            @PathVariable Long id,
            @Valid @RequestBody SeasonDto seasonDto) {

        Season entityToUpdate = mapper.toEntity(seasonDto);
        Season updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        return doDelete(id);
    }
}