package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TourDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TourSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Tour;
import com.example.modules.entertainment_ecosystem.mapper.TourMapper;
import com.example.modules.entertainment_ecosystem.service.TourService;
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
 * Controller for managing Tour entities.
 */
@RestController
@RequestMapping("/api/tours")
public class TourController extends BaseController<Tour, TourDto, TourSimpleDto> {

    public TourController(TourService tourService,
                                    TourMapper tourMapper) {
        super(tourService, tourMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TourDto>> getAllTours(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TourDto>> searchTours(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Tour.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TourDto> createTour(
            @Valid @RequestBody TourDto tourDto,
            UriComponentsBuilder uriBuilder) {

        Tour entity = mapper.toEntity(tourDto);
        Tour saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/tours/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TourDto>> createAllTours(
            @Valid @RequestBody List<TourDto> tourDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Tour> entities = mapper.toEntityList(tourDtoList);
        List<Tour> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/tours").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDto> updateTour(
            @PathVariable Long id,
            @Valid @RequestBody TourDto tourDto) {

        Tour entityToUpdate = mapper.toEntity(tourDto);
        Tour updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        return doDelete(id);
    }
}