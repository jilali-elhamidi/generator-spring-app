package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.DocumentTypeDto;
import com.example.modules.project_management.model.DocumentType;
import com.example.modules.project_management.mapper.DocumentTypeMapper;
import com.example.modules.project_management.service.DocumentTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/documenttypes")
public class DocumentTypeController {

    private final DocumentTypeService documenttypeService;
    private final DocumentTypeMapper documenttypeMapper;

    public DocumentTypeController(DocumentTypeService documenttypeService,
                                    DocumentTypeMapper documenttypeMapper) {
        this.documenttypeService = documenttypeService;
        this.documenttypeMapper = documenttypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<DocumentTypeDto>> getAllDocumentTypes() {
        List<DocumentType> entities = documenttypeService.findAll();
        return ResponseEntity.ok(documenttypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeDto> getDocumentTypeById(@PathVariable Long id) {
        return documenttypeService.findById(id)
                .map(documenttypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DocumentTypeDto> createDocumentType(
            @Valid @RequestBody DocumentTypeDto documenttypeDto,
            UriComponentsBuilder uriBuilder) {

        DocumentType entity = documenttypeMapper.toEntity(documenttypeDto);
        DocumentType saved = documenttypeService.save(entity);

        URI location = uriBuilder
                                .path("/api/documenttypes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(documenttypeMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeDto> updateDocumentType(
            @PathVariable Long id,
            @Valid @RequestBody DocumentTypeDto documenttypeDto) {


        DocumentType entityToUpdate = documenttypeMapper.toEntity(documenttypeDto);
        DocumentType updatedEntity = documenttypeService.update(id, entityToUpdate);

        return ResponseEntity.ok(documenttypeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable Long id) {
        boolean deleted = documenttypeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}