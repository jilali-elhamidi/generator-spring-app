package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentProviderDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentProviderSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.mapper.ContentProviderMapper;
import com.example.modules.entertainment_ecosystem.service.ContentProviderService;
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
 * Controller for managing ContentProvider entities.
 */
@RestController
@RequestMapping("/api/contentproviders")
public class ContentProviderController extends BaseController<ContentProvider, ContentProviderDto, ContentProviderSimpleDto> {

    public ContentProviderController(ContentProviderService contentproviderService,
                                    ContentProviderMapper contentproviderMapper) {
        super(contentproviderService, contentproviderMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ContentProviderDto>> getAllContentProviders(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContentProviderDto>> searchContentProviders(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ContentProvider.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentProviderDto> getContentProviderById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ContentProviderDto> createContentProvider(
            @Valid @RequestBody ContentProviderDto contentproviderDto,
            UriComponentsBuilder uriBuilder) {

        ContentProvider entity = mapper.toEntity(contentproviderDto);
        ContentProvider saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/contentproviders/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContentProviderDto>> createAllContentProviders(
            @Valid @RequestBody List<ContentProviderDto> contentproviderDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ContentProvider> entities = mapper.toEntityList(contentproviderDtoList);
        List<ContentProvider> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/contentproviders").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentProviderDto> updateContentProvider(
            @PathVariable Long id,
            @Valid @RequestBody ContentProviderDto contentproviderDto) {

        ContentProvider entityToUpdate = mapper.toEntity(contentproviderDto);
        ContentProvider updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentProvider(@PathVariable Long id) {
        return doDelete(id);
    }
}