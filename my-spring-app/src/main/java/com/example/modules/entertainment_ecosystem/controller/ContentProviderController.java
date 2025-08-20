package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentProviderDto;
import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.mapper.ContentProviderMapper;
import com.example.modules.entertainment_ecosystem.service.ContentProviderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contentproviders")
public class ContentProviderController {

    private final ContentProviderService contentproviderService;
    private final ContentProviderMapper contentproviderMapper;

    public ContentProviderController(ContentProviderService contentproviderService,
                                    ContentProviderMapper contentproviderMapper) {
        this.contentproviderService = contentproviderService;
        this.contentproviderMapper = contentproviderMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContentProviderDto>> getAllContentProviders() {
        List<ContentProvider> entities = contentproviderService.findAll();
        return ResponseEntity.ok(contentproviderMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentProviderDto> getContentProviderById(@PathVariable Long id) {
        return contentproviderService.findById(id)
                .map(contentproviderMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContentProviderDto> createContentProvider(
            @Valid @RequestBody ContentProviderDto contentproviderDto,
            UriComponentsBuilder uriBuilder) {

        ContentProvider entity = contentproviderMapper.toEntity(contentproviderDto);
        ContentProvider saved = contentproviderService.save(entity);

        URI location = uriBuilder
                                .path("/api/contentproviders/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(contentproviderMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentProviderDto> updateContentProvider(
            @PathVariable Long id,
            @Valid @RequestBody ContentProviderDto contentproviderDto) {


        ContentProvider entityToUpdate = contentproviderMapper.toEntity(contentproviderDto);
        ContentProvider updatedEntity = contentproviderService.update(id, entityToUpdate);

        return ResponseEntity.ok(contentproviderMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentProvider(@PathVariable Long id) {
        boolean deleted = contentproviderService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}