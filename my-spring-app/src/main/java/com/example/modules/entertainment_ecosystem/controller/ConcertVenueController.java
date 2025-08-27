package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ConcertVenueDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ConcertVenueSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ConcertVenue;
import com.example.modules.entertainment_ecosystem.mapper.ConcertVenueMapper;
import com.example.modules.entertainment_ecosystem.service.ConcertVenueService;
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
 * Controller for managing ConcertVenue entities.
 */
@RestController
@RequestMapping("/api/concertvenues")
public class ConcertVenueController extends BaseController<ConcertVenue, ConcertVenueDto, ConcertVenueSimpleDto> {

    public ConcertVenueController(ConcertVenueService concertvenueService,
                                    ConcertVenueMapper concertvenueMapper) {
        super(concertvenueService, concertvenueMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ConcertVenueDto>> getAllConcertVenues(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ConcertVenueDto>> searchConcertVenues(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ConcertVenue.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcertVenueDto> getConcertVenueById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ConcertVenueDto> createConcertVenue(
            @Valid @RequestBody ConcertVenueDto concertvenueDto,
            UriComponentsBuilder uriBuilder) {

        ConcertVenue entity = mapper.toEntity(concertvenueDto);
        ConcertVenue saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/concertvenues/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ConcertVenueDto>> createAllConcertVenues(
            @Valid @RequestBody List<ConcertVenueDto> concertvenueDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ConcertVenue> entities = mapper.toEntityList(concertvenueDtoList);
        List<ConcertVenue> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/concertvenues").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConcertVenueDto> updateConcertVenue(
            @PathVariable Long id,
            @Valid @RequestBody ConcertVenueDto concertvenueDto) {

        ConcertVenue entityToUpdate = mapper.toEntity(concertvenueDto);
        ConcertVenue updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcertVenue(@PathVariable Long id) {
        return doDelete(id);
    }
}