package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TourDto;
import com.example.modules.entertainment_ecosystem.model.Tour;
import com.example.modules.entertainment_ecosystem.mapper.TourMapper;
import com.example.modules.entertainment_ecosystem.service.TourService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourService tourService;
    private final TourMapper tourMapper;

    public TourController(TourService tourService,
                                    TourMapper tourMapper) {
        this.tourService = tourService;
        this.tourMapper = tourMapper;
    }

    @GetMapping
    public ResponseEntity<List<TourDto>> getAllTours() {
        List<Tour> entities = tourService.findAll();
        return ResponseEntity.ok(tourMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(@PathVariable Long id) {
        return tourService.findById(id)
                .map(tourMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TourDto> createTour(
            @Valid @RequestBody TourDto tourDto,
            UriComponentsBuilder uriBuilder) {

        Tour entity = tourMapper.toEntity(tourDto);
        Tour saved = tourService.save(entity);
        URI location = uriBuilder.path("/api/tours/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(tourMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<TourDto> updateTour(
                @PathVariable Long id,
                @RequestBody TourDto tourDto) {

                // Transformer le DTO en entity pour le service
                Tour entityToUpdate = tourMapper.toEntity(tourDto);

                // Appel du service update
                Tour updatedEntity = tourService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                TourDto updatedDto = tourMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
                    boolean deleted = tourService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}