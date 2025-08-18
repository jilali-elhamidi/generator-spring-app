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
        URI location = uriBuilder.path("/api/documents/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(documentMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<DocumentDto> updateDocument(
                @PathVariable Long id,
                @RequestBody DocumentDto documentDto) {

                // Transformer le DTO en entity pour le service
                Document entityToUpdate = documentMapper.toEntity(documentDto);

                // Appel du service update
                Document updatedEntity = documentService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                DocumentDto updatedDto = documentMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
                    boolean deleted = documentService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}