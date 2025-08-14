package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.LicenseDto;
import com.example.modules.entertainment_ecosystem.model.License;
import com.example.modules.entertainment_ecosystem.mapper.LicenseMapper;
import com.example.modules.entertainment_ecosystem.service.LicenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/licenses")
public class LicenseController {

    private final LicenseService licenseService;
    private final LicenseMapper licenseMapper;

    public LicenseController(LicenseService licenseService,
                                    LicenseMapper licenseMapper) {
        this.licenseService = licenseService;
        this.licenseMapper = licenseMapper;
    }

    @GetMapping
    public ResponseEntity<List<LicenseDto>> getAllLicenses() {
        List<License> entities = licenseService.findAll();
        return ResponseEntity.ok(licenseMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseDto> getLicenseById(@PathVariable Long id) {
        return licenseService.findById(id)
                .map(licenseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LicenseDto> createLicense(
            @Valid @RequestBody LicenseDto licenseDto,
            UriComponentsBuilder uriBuilder) {

        License entity = licenseMapper.toEntity(licenseDto);
        License saved = licenseService.save(entity);
        URI location = uriBuilder.path("/api/licenses/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(licenseMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<LicenseDto> updateLicense(
                @PathVariable Long id,
                @Valid @RequestBody LicenseDto licenseDto) {

                try {
                // Récupérer l'entité existante avec Optional
                License existing = licenseService.findById(id)
                .orElseThrow(() -> new RuntimeException("License not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                licenseMapper.updateEntityFromDto(licenseDto, existing);

                // Sauvegarde
                License updatedEntity = licenseService.save(existing);

                return ResponseEntity.ok(licenseMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        licenseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}