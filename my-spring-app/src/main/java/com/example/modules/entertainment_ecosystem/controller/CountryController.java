package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.CountryDto;
import com.example.modules.entertainment_ecosystem.model.Country;
import com.example.modules.entertainment_ecosystem.mapper.CountryMapper;
import com.example.modules.entertainment_ecosystem.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/countrys")
public class CountryController {

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    public CountryController(CountryService countryService,
                                    CountryMapper countryMapper) {
        this.countryService = countryService;
        this.countryMapper = countryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountrys() {
        List<Country> entities = countryService.findAll();
        return ResponseEntity.ok(countryMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        return countryService.findById(id)
                .map(countryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CountryDto> createCountry(
            @Valid @RequestBody CountryDto countryDto,
            UriComponentsBuilder uriBuilder) {

        Country entity = countryMapper.toEntity(countryDto);
        Country saved = countryService.save(entity);
        URI location = uriBuilder.path("/api/countrys/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(countryMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<CountryDto> updateCountry(
                @PathVariable Long id,
                @RequestBody CountryDto countryDto) {

                // Transformer le DTO en entity pour le service
                Country entityToUpdate = countryMapper.toEntity(countryDto);

                // Appel du service update
                Country updatedEntity = countryService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                CountryDto updatedDto = countryMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
                    boolean deleted = countryService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}