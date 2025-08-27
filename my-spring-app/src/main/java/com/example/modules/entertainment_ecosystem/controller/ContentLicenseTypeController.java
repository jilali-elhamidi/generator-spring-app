package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentLicenseTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentLicenseTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ContentLicenseType;
import com.example.modules.entertainment_ecosystem.mapper.ContentLicenseTypeMapper;
import com.example.modules.entertainment_ecosystem.service.ContentLicenseTypeService;
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
 * Controller for managing ContentLicenseType entities.
 */
@RestController
@RequestMapping("/api/contentlicensetypes")
public class ContentLicenseTypeController extends BaseController<ContentLicenseType, ContentLicenseTypeDto, ContentLicenseTypeSimpleDto> {

    public ContentLicenseTypeController(ContentLicenseTypeService contentlicensetypeService,
                                    ContentLicenseTypeMapper contentlicensetypeMapper) {
        super(contentlicensetypeService, contentlicensetypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ContentLicenseTypeDto>> getAllContentLicenseTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContentLicenseTypeDto>> searchContentLicenseTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ContentLicenseType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentLicenseTypeDto> getContentLicenseTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ContentLicenseTypeDto> createContentLicenseType(
            @Valid @RequestBody ContentLicenseTypeDto contentlicensetypeDto,
            UriComponentsBuilder uriBuilder) {

        ContentLicenseType entity = mapper.toEntity(contentlicensetypeDto);
        ContentLicenseType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/contentlicensetypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContentLicenseTypeDto>> createAllContentLicenseTypes(
            @Valid @RequestBody List<ContentLicenseTypeDto> contentlicensetypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ContentLicenseType> entities = mapper.toEntityList(contentlicensetypeDtoList);
        List<ContentLicenseType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/contentlicensetypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentLicenseTypeDto> updateContentLicenseType(
            @PathVariable Long id,
            @Valid @RequestBody ContentLicenseTypeDto contentlicensetypeDto) {

        ContentLicenseType entityToUpdate = mapper.toEntity(contentlicensetypeDto);
        ContentLicenseType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentLicenseType(@PathVariable Long id) {
        return doDelete(id);
    }
}