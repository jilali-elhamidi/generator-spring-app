package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.DocumentDto;
import com.example.modules.project_management.model.Document;
import com.example.modules.project_management.mapper.DocumentMapper;
import com.example.modules.project_management.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    public DocumentController(DocumentService documentService,
                                    DocumentMapper documentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAllDocuments() {
        List<Document> entities = documentService.findAll();
        return ResponseEntity.ok(documentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable Long id) {
        return documentService.findById(id)
                .map(documentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DocumentDto> createDocument(
            @Valid @RequestBody DocumentDto documentDto,
            UriComponentsBuilder uriBuilder) {

        Document entity = documentMapper.toEntity(documentDto);
        Document saved = documentService.save(entity);

        URI location = uriBuilder
                                .path("/api/documents/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(documentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentDto documentDto) {


        Document entityToUpdate = documentMapper.toEntity(documentDto);
        Document updatedEntity = documentService.update(id, entityToUpdate);

        return ResponseEntity.ok(documentMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        boolean deleted = documentService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}