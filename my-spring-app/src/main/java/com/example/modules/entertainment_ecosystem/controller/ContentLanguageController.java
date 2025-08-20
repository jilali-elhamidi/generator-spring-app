package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentLanguageDto;
import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import com.example.modules.entertainment_ecosystem.mapper.ContentLanguageMapper;
import com.example.modules.entertainment_ecosystem.service.ContentLanguageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contentlanguages")
public class ContentLanguageController {

    private final ContentLanguageService contentlanguageService;
    private final ContentLanguageMapper contentlanguageMapper;

    public ContentLanguageController(ContentLanguageService contentlanguageService,
                                    ContentLanguageMapper contentlanguageMapper) {
        this.contentlanguageService = contentlanguageService;
        this.contentlanguageMapper = contentlanguageMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContentLanguageDto>> getAllContentLanguages() {
        List<ContentLanguage> entities = contentlanguageService.findAll();
        return ResponseEntity.ok(contentlanguageMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentLanguageDto> getContentLanguageById(@PathVariable Long id) {
        return contentlanguageService.findById(id)
                .map(contentlanguageMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContentLanguageDto> createContentLanguage(
            @Valid @RequestBody ContentLanguageDto contentlanguageDto,
            UriComponentsBuilder uriBuilder) {

        ContentLanguage entity = contentlanguageMapper.toEntity(contentlanguageDto);
        ContentLanguage saved = contentlanguageService.save(entity);

        URI location = uriBuilder
                                .path("/api/contentlanguages/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(contentlanguageMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentLanguageDto> updateContentLanguage(
            @PathVariable Long id,
            @Valid @RequestBody ContentLanguageDto contentlanguageDto) {


        ContentLanguage entityToUpdate = contentlanguageMapper.toEntity(contentlanguageDto);
        ContentLanguage updatedEntity = contentlanguageService.update(id, entityToUpdate);

        return ResponseEntity.ok(contentlanguageMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentLanguage(@PathVariable Long id) {
        boolean deleted = contentlanguageService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}