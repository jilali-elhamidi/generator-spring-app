package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.DocumentDto;
import com.example.modules.project_management.dtosimple.DocumentSimpleDto;
import com.example.modules.project_management.model.Document;
import com.example.modules.project_management.mapper.DocumentMapper;
import com.example.modules.project_management.service.DocumentService;
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
 * Controller for managing Document entities.
 */
@RestController
@RequestMapping("/api/documents")
public class DocumentController extends BaseController<Document, DocumentDto, DocumentSimpleDto> {

    public DocumentController(DocumentService documentService,
                                    DocumentMapper documentMapper) {
        super(documentService, documentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<DocumentDto>> getAllDocuments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DocumentDto>> searchDocuments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Document.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<DocumentDto> createDocument(
            @Valid @RequestBody DocumentDto documentDto,
            UriComponentsBuilder uriBuilder) {

        Document entity = mapper.toEntity(documentDto);
        Document saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/documents/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DocumentDto>> createAllDocuments(
            @Valid @RequestBody List<DocumentDto> documentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Document> entities = mapper.toEntityList(documentDtoList);
        List<Document> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/documents").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentDto documentDto) {

        Document entityToUpdate = mapper.toEntity(documentDto);
        Document updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        return doDelete(id);
    }
}