package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.DocumentTypeDto;
import com.example.modules.project_management.dtosimple.DocumentTypeSimpleDto;
import com.example.modules.project_management.model.DocumentType;
import com.example.modules.project_management.mapper.DocumentTypeMapper;
import com.example.modules.project_management.service.DocumentTypeService;
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
 * Controller for managing DocumentType entities.
 */
@RestController
@RequestMapping("/api/documenttypes")
public class DocumentTypeController extends BaseController<DocumentType, DocumentTypeDto, DocumentTypeSimpleDto> {

    public DocumentTypeController(DocumentTypeService documenttypeService,
                                    DocumentTypeMapper documenttypeMapper) {
        super(documenttypeService, documenttypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<DocumentTypeDto>> getAllDocumentTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DocumentTypeDto>> searchDocumentTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(DocumentType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeDto> getDocumentTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<DocumentTypeDto> createDocumentType(
            @Valid @RequestBody DocumentTypeDto documenttypeDto,
            UriComponentsBuilder uriBuilder) {

        DocumentType entity = mapper.toEntity(documenttypeDto);
        DocumentType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/documenttypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DocumentTypeDto>> createAllDocumentTypes(
            @Valid @RequestBody List<DocumentTypeDto> documenttypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<DocumentType> entities = mapper.toEntityList(documenttypeDtoList);
        List<DocumentType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/documenttypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeDto> updateDocumentType(
            @PathVariable Long id,
            @Valid @RequestBody DocumentTypeDto documenttypeDto) {

        DocumentType entityToUpdate = mapper.toEntity(documenttypeDto);
        DocumentType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable Long id) {
        return doDelete(id);
    }
}