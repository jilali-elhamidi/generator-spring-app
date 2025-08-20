package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ConcertVenueDto;
import com.example.modules.entertainment_ecosystem.model.ConcertVenue;
import com.example.modules.entertainment_ecosystem.mapper.ConcertVenueMapper;
import com.example.modules.entertainment_ecosystem.service.ConcertVenueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/concertvenues")
public class ConcertVenueController {

    private final ConcertVenueService concertvenueService;
    private final ConcertVenueMapper concertvenueMapper;

    public ConcertVenueController(ConcertVenueService concertvenueService,
                                    ConcertVenueMapper concertvenueMapper) {
        this.concertvenueService = concertvenueService;
        this.concertvenueMapper = concertvenueMapper;
    }

    @GetMapping
    public ResponseEntity<List<ConcertVenueDto>> getAllConcertVenues() {
        List<ConcertVenue> entities = concertvenueService.findAll();
        return ResponseEntity.ok(concertvenueMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcertVenueDto> getConcertVenueById(@PathVariable Long id) {
        return concertvenueService.findById(id)
                .map(concertvenueMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConcertVenueDto> createConcertVenue(
            @Valid @RequestBody ConcertVenueDto concertvenueDto,
            UriComponentsBuilder uriBuilder) {

        ConcertVenue entity = concertvenueMapper.toEntity(concertvenueDto);
        ConcertVenue saved = concertvenueService.save(entity);
        URI location = uriBuilder.path("/api/concertvenues/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(concertvenueMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ConcertVenueDto> updateConcertVenue(
                @PathVariable Long id,
                @RequestBody ConcertVenueDto concertvenueDto) {

                // Transformer le DTO en entity pour le service
                ConcertVenue entityToUpdate = concertvenueMapper.toEntity(concertvenueDto);

                // Appel du service update
                ConcertVenue updatedEntity = concertvenueService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ConcertVenueDto updatedDto = concertvenueMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteConcertVenue(@PathVariable Long id) {
                    boolean deleted = concertvenueService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}