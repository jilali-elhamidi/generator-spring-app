package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EpisodeCreditDto;
import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;
import com.example.modules.entertainment_ecosystem.mapper.EpisodeCreditMapper;
import com.example.modules.entertainment_ecosystem.service.EpisodeCreditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/episodecredits")
public class EpisodeCreditController {

    private final EpisodeCreditService episodecreditService;
    private final EpisodeCreditMapper episodecreditMapper;

    public EpisodeCreditController(EpisodeCreditService episodecreditService,
                                    EpisodeCreditMapper episodecreditMapper) {
        this.episodecreditService = episodecreditService;
        this.episodecreditMapper = episodecreditMapper;
    }

    @GetMapping
    public ResponseEntity<List<EpisodeCreditDto>> getAllEpisodeCredits() {
        List<EpisodeCredit> entities = episodecreditService.findAll();
        return ResponseEntity.ok(episodecreditMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeCreditDto> getEpisodeCreditById(@PathVariable Long id) {
        return episodecreditService.findById(id)
                .map(episodecreditMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EpisodeCreditDto> createEpisodeCredit(
            @Valid @RequestBody EpisodeCreditDto episodecreditDto,
            UriComponentsBuilder uriBuilder) {

        EpisodeCredit entity = episodecreditMapper.toEntity(episodecreditDto);
        EpisodeCredit saved = episodecreditService.save(entity);
        URI location = uriBuilder.path("/api/episodecredits/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(episodecreditMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpisodeCreditDto> updateEpisodeCredit(
            @PathVariable Long id,
            @Valid @RequestBody EpisodeCreditDto episodecreditDto) {

        try {
            EpisodeCredit updatedEntity = episodecreditService.update(
                    id,
                    episodecreditMapper.toEntity(episodecreditDto)
            );
            return ResponseEntity.ok(episodecreditMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisodeCredit(@PathVariable Long id) {
        episodecreditService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}