package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EpisodeCreditDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeCreditSimpleDto;
import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;
import com.example.modules.entertainment_ecosystem.mapper.EpisodeCreditMapper;
import com.example.modules.entertainment_ecosystem.service.EpisodeCreditService;
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
 * Controller for managing EpisodeCredit entities.
 */
@RestController
@RequestMapping("/api/episodecredits")
public class EpisodeCreditController extends BaseController<EpisodeCredit, EpisodeCreditDto, EpisodeCreditSimpleDto> {

    public EpisodeCreditController(EpisodeCreditService episodecreditService,
                                    EpisodeCreditMapper episodecreditMapper) {
        super(episodecreditService, episodecreditMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EpisodeCreditDto>> getAllEpisodeCredits(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EpisodeCreditDto>> searchEpisodeCredits(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(EpisodeCredit.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeCreditDto> getEpisodeCreditById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EpisodeCreditDto> createEpisodeCredit(
            @Valid @RequestBody EpisodeCreditDto episodecreditDto,
            UriComponentsBuilder uriBuilder) {

        EpisodeCredit entity = mapper.toEntity(episodecreditDto);
        EpisodeCredit saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/episodecredits/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EpisodeCreditDto>> createAllEpisodeCredits(
            @Valid @RequestBody List<EpisodeCreditDto> episodecreditDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EpisodeCredit> entities = mapper.toEntityList(episodecreditDtoList);
        List<EpisodeCredit> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/episodecredits").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpisodeCreditDto> updateEpisodeCredit(
            @PathVariable Long id,
            @Valid @RequestBody EpisodeCreditDto episodecreditDto) {

        EpisodeCredit entityToUpdate = mapper.toEntity(episodecreditDto);
        EpisodeCredit updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisodeCredit(@PathVariable Long id) {
        return doDelete(id);
    }
}