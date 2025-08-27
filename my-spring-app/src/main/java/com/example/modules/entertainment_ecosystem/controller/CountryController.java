package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.CountryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.CountrySimpleDto;
import com.example.modules.entertainment_ecosystem.model.Country;
import com.example.modules.entertainment_ecosystem.mapper.CountryMapper;
import com.example.modules.entertainment_ecosystem.service.CountryService;
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
 * Controller for managing Country entities.
 */
@RestController
@RequestMapping("/api/countrys")
public class CountryController extends BaseController<Country, CountryDto, CountrySimpleDto> {

    public CountryController(CountryService countryService,
                                    CountryMapper countryMapper) {
        super(countryService, countryMapper);
    }

    @GetMapping
    public ResponseEntity<Page<CountryDto>> getAllCountrys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CountryDto>> searchCountrys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Country.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<CountryDto> createCountry(
            @Valid @RequestBody CountryDto countryDto,
            UriComponentsBuilder uriBuilder) {

        Country entity = mapper.toEntity(countryDto);
        Country saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/countrys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CountryDto>> createAllCountrys(
            @Valid @RequestBody List<CountryDto> countryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Country> entities = mapper.toEntityList(countryDtoList);
        List<Country> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/countrys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(
            @PathVariable Long id,
            @Valid @RequestBody CountryDto countryDto) {

        Country entityToUpdate = mapper.toEntity(countryDto);
        Country updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        return doDelete(id);
    }
}