package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentLicenseTypeDto;
import com.example.modules.entertainment_ecosystem.model.ContentLicenseType;
import com.example.modules.entertainment_ecosystem.mapper.ContentLicenseTypeMapper;
import com.example.modules.entertainment_ecosystem.service.ContentLicenseTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contentlicensetypes")
public class ContentLicenseTypeController {

    private final ContentLicenseTypeService contentlicensetypeService;
    private final ContentLicenseTypeMapper contentlicensetypeMapper;

    public ContentLicenseTypeController(ContentLicenseTypeService contentlicensetypeService,
                                    ContentLicenseTypeMapper contentlicensetypeMapper) {
        this.contentlicensetypeService = contentlicensetypeService;
        this.contentlicensetypeMapper = contentlicensetypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContentLicenseTypeDto>> getAllContentLicenseTypes() {
        List<ContentLicenseType> entities = contentlicensetypeService.findAll();
        return ResponseEntity.ok(contentlicensetypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentLicenseTypeDto> getContentLicenseTypeById(@PathVariable Long id) {
        return contentlicensetypeService.findById(id)
                .map(contentlicensetypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContentLicenseTypeDto> createContentLicenseType(
            @Valid @RequestBody ContentLicenseTypeDto contentlicensetypeDto,
            UriComponentsBuilder uriBuilder) {

        ContentLicenseType entity = contentlicensetypeMapper.toEntity(contentlicensetypeDto);
        ContentLicenseType saved = contentlicensetypeService.save(entity);
        URI location = uriBuilder.path("/api/contentlicensetypes/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(contentlicensetypeMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ContentLicenseTypeDto> updateContentLicenseType(
                @PathVariable Long id,
                @RequestBody ContentLicenseTypeDto contentlicensetypeDto) {

                // Transformer le DTO en entity pour le service
                ContentLicenseType entityToUpdate = contentlicensetypeMapper.toEntity(contentlicensetypeDto);

                // Appel du service update
                ContentLicenseType updatedEntity = contentlicensetypeService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ContentLicenseTypeDto updatedDto = contentlicensetypeMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteContentLicenseType(@PathVariable Long id) {
                    boolean deleted = contentlicensetypeService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}