package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.AttachmentDto;
import com.example.modules.project_management.model.Attachment;
import com.example.modules.project_management.mapper.AttachmentMapper;
import com.example.modules.project_management.service.AttachmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentMapper attachmentMapper;

    public AttachmentController(AttachmentService attachmentService,
                                    AttachmentMapper attachmentMapper) {
        this.attachmentService = attachmentService;
        this.attachmentMapper = attachmentMapper;
    }

    @GetMapping
    public ResponseEntity<List<AttachmentDto>> getAllAttachments() {
        List<Attachment> entities = attachmentService.findAll();
        return ResponseEntity.ok(attachmentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentDto> getAttachmentById(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(attachmentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AttachmentDto> createAttachment(
            @Valid @RequestBody AttachmentDto attachmentDto,
            UriComponentsBuilder uriBuilder) {

        Attachment entity = attachmentMapper.toEntity(attachmentDto);
        Attachment saved = attachmentService.save(entity);
        URI location = uriBuilder.path("/api/attachments/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(attachmentMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<AttachmentDto> updateAttachment(
                @PathVariable Long id,
                @RequestBody AttachmentDto attachmentDto) {

                // Transformer le DTO en entity pour le service
                Attachment entityToUpdate = attachmentMapper.toEntity(attachmentDto);

                // Appel du service update
                Attachment updatedEntity = attachmentService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                AttachmentDto updatedDto = attachmentMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
                    boolean deleted = attachmentService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}