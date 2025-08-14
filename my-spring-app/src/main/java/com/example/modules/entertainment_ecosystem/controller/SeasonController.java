package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SeasonDto;
import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.mapper.SeasonMapper;
import com.example.modules.entertainment_ecosystem.service.SeasonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/seasons")
public class SeasonController {

    private final SeasonService seasonService;
    private final SeasonMapper seasonMapper;

    public SeasonController(SeasonService seasonService,
                                    SeasonMapper seasonMapper) {
        this.seasonService = seasonService;
        this.seasonMapper = seasonMapper;
    }

    @GetMapping
    public ResponseEntity<List<SeasonDto>> getAllSeasons() {
        List<Season> entities = seasonService.findAll();
        return ResponseEntity.ok(seasonMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonDto> getSeasonById(@PathVariable Long id) {
        return seasonService.findById(id)
                .map(seasonMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SeasonDto> createSeason(
            @Valid @RequestBody SeasonDto seasonDto,
            UriComponentsBuilder uriBuilder) {

        Season entity = seasonMapper.toEntity(seasonDto);
        Season saved = seasonService.save(entity);
        URI location = uriBuilder.path("/api/seasons/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(seasonMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<SeasonDto> updateSeason(
                @PathVariable Long id,
                @Valid @RequestBody SeasonDto seasonDto) {

                try {
                // Récupérer l'entité existante avec Optional
                Season existing = seasonService.findById(id)
                .orElseThrow(() -> new RuntimeException("Season not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                seasonMapper.updateEntityFromDto(seasonDto, existing);

                // Sauvegarde
                Season updatedEntity = seasonService.save(existing);

                return ResponseEntity.ok(seasonMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        seasonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}