package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.LicenseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LicenseSimpleDto;
import com.example.modules.entertainment_ecosystem.model.License;
import com.example.modules.entertainment_ecosystem.mapper.LicenseMapper;
import com.example.modules.entertainment_ecosystem.service.LicenseService;
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
 * Controller for managing License entities.
 */
@RestController
@RequestMapping("/api/licenses")
public class LicenseController extends BaseController<License, LicenseDto, LicenseSimpleDto> {

    public LicenseController(LicenseService licenseService,
                                    LicenseMapper licenseMapper) {
        super(licenseService, licenseMapper);
    }

    @GetMapping
    public ResponseEntity<Page<LicenseDto>> getAllLicenses(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<LicenseDto>> searchLicenses(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(License.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseDto> getLicenseById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<LicenseDto> createLicense(
            @Valid @RequestBody LicenseDto licenseDto,
            UriComponentsBuilder uriBuilder) {

        License entity = mapper.toEntity(licenseDto);
        License saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/licenses/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LicenseDto>> createAllLicenses(
            @Valid @RequestBody List<LicenseDto> licenseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<License> entities = mapper.toEntityList(licenseDtoList);
        List<License> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/licenses").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseDto> updateLicense(
            @PathVariable Long id,
            @Valid @RequestBody LicenseDto licenseDto) {

        License entityToUpdate = mapper.toEntity(licenseDto);
        License updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        return doDelete(id);
    }
}