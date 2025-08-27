package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.AttachmentDto;
import com.example.modules.project_management.dtosimple.AttachmentSimpleDto;
import com.example.modules.project_management.model.Attachment;
import com.example.modules.project_management.mapper.AttachmentMapper;
import com.example.modules.project_management.service.AttachmentService;
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
 * Controller for managing Attachment entities.
 */
@RestController
@RequestMapping("/api/attachments")
public class AttachmentController extends BaseController<Attachment, AttachmentDto, AttachmentSimpleDto> {

    public AttachmentController(AttachmentService attachmentService,
                                    AttachmentMapper attachmentMapper) {
        super(attachmentService, attachmentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AttachmentDto>> getAllAttachments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AttachmentDto>> searchAttachments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Attachment.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentDto> getAttachmentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AttachmentDto> createAttachment(
            @Valid @RequestBody AttachmentDto attachmentDto,
            UriComponentsBuilder uriBuilder) {

        Attachment entity = mapper.toEntity(attachmentDto);
        Attachment saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/attachments/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AttachmentDto>> createAllAttachments(
            @Valid @RequestBody List<AttachmentDto> attachmentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Attachment> entities = mapper.toEntityList(attachmentDtoList);
        List<Attachment> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/attachments").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttachmentDto> updateAttachment(
            @PathVariable Long id,
            @Valid @RequestBody AttachmentDto attachmentDto) {

        Attachment entityToUpdate = mapper.toEntity(attachmentDto);
        Attachment updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        return doDelete(id);
    }
}