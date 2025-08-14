package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.StreamingContentLicenseDto;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.mapper.StreamingContentLicenseMapper;
import com.example.modules.entertainment_ecosystem.service.StreamingContentLicenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/streamingcontentlicenses")
public class StreamingContentLicenseController {

    private final StreamingContentLicenseService streamingcontentlicenseService;
    private final StreamingContentLicenseMapper streamingcontentlicenseMapper;

    public StreamingContentLicenseController(StreamingContentLicenseService streamingcontentlicenseService,
                                    StreamingContentLicenseMapper streamingcontentlicenseMapper) {
        this.streamingcontentlicenseService = streamingcontentlicenseService;
        this.streamingcontentlicenseMapper = streamingcontentlicenseMapper;
    }

    @GetMapping
    public ResponseEntity<List<StreamingContentLicenseDto>> getAllStreamingContentLicenses() {
        List<StreamingContentLicense> entities = streamingcontentlicenseService.findAll();
        return ResponseEntity.ok(streamingcontentlicenseMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingContentLicenseDto> getStreamingContentLicenseById(@PathVariable Long id) {
        return streamingcontentlicenseService.findById(id)
                .map(streamingcontentlicenseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StreamingContentLicenseDto> createStreamingContentLicense(
            @Valid @RequestBody StreamingContentLicenseDto streamingcontentlicenseDto,
            UriComponentsBuilder uriBuilder) {

        StreamingContentLicense entity = streamingcontentlicenseMapper.toEntity(streamingcontentlicenseDto);
        StreamingContentLicense saved = streamingcontentlicenseService.save(entity);
        URI location = uriBuilder.path("/api/streamingcontentlicenses/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(streamingcontentlicenseMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<StreamingContentLicenseDto> updateStreamingContentLicense(
                @PathVariable Long id,
                @Valid @RequestBody StreamingContentLicenseDto streamingcontentlicenseDto) {

                try {
                // Récupérer l'entité existante avec Optional
                StreamingContentLicense existing = streamingcontentlicenseService.findById(id)
                .orElseThrow(() -> new RuntimeException("StreamingContentLicense not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                streamingcontentlicenseMapper.updateEntityFromDto(streamingcontentlicenseDto, existing);

                // Sauvegarde
                StreamingContentLicense updatedEntity = streamingcontentlicenseService.save(existing);

                return ResponseEntity.ok(streamingcontentlicenseMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamingContentLicense(@PathVariable Long id) {
        streamingcontentlicenseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}