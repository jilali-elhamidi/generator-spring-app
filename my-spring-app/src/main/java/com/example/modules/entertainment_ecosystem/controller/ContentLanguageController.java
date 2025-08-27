package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentLanguageDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentLanguageSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import com.example.modules.entertainment_ecosystem.mapper.ContentLanguageMapper;
import com.example.modules.entertainment_ecosystem.service.ContentLanguageService;
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
 * Controller for managing ContentLanguage entities.
 */
@RestController
@RequestMapping("/api/contentlanguages")
public class ContentLanguageController extends BaseController<ContentLanguage, ContentLanguageDto, ContentLanguageSimpleDto> {

    public ContentLanguageController(ContentLanguageService contentlanguageService,
                                    ContentLanguageMapper contentlanguageMapper) {
        super(contentlanguageService, contentlanguageMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ContentLanguageDto>> getAllContentLanguages(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContentLanguageDto>> searchContentLanguages(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ContentLanguage.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentLanguageDto> getContentLanguageById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ContentLanguageDto> createContentLanguage(
            @Valid @RequestBody ContentLanguageDto contentlanguageDto,
            UriComponentsBuilder uriBuilder) {

        ContentLanguage entity = mapper.toEntity(contentlanguageDto);
        ContentLanguage saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/contentlanguages/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContentLanguageDto>> createAllContentLanguages(
            @Valid @RequestBody List<ContentLanguageDto> contentlanguageDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ContentLanguage> entities = mapper.toEntityList(contentlanguageDtoList);
        List<ContentLanguage> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/contentlanguages").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentLanguageDto> updateContentLanguage(
            @PathVariable Long id,
            @Valid @RequestBody ContentLanguageDto contentlanguageDto) {

        ContentLanguage entityToUpdate = mapper.toEntity(contentlanguageDto);
        ContentLanguage updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentLanguage(@PathVariable Long id) {
        return doDelete(id);
    }
}