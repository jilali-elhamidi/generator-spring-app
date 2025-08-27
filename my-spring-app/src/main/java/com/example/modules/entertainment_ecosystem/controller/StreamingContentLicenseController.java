package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.StreamingContentLicenseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingContentLicenseSimpleDto;
import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.mapper.StreamingContentLicenseMapper;
import com.example.modules.entertainment_ecosystem.service.StreamingContentLicenseService;
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
 * Controller for managing StreamingContentLicense entities.
 */
@RestController
@RequestMapping("/api/streamingcontentlicenses")
public class StreamingContentLicenseController extends BaseController<StreamingContentLicense, StreamingContentLicenseDto, StreamingContentLicenseSimpleDto> {

    public StreamingContentLicenseController(StreamingContentLicenseService streamingcontentlicenseService,
                                    StreamingContentLicenseMapper streamingcontentlicenseMapper) {
        super(streamingcontentlicenseService, streamingcontentlicenseMapper);
    }

    @GetMapping
    public ResponseEntity<Page<StreamingContentLicenseDto>> getAllStreamingContentLicenses(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StreamingContentLicenseDto>> searchStreamingContentLicenses(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(StreamingContentLicense.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingContentLicenseDto> getStreamingContentLicenseById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<StreamingContentLicenseDto> createStreamingContentLicense(
            @Valid @RequestBody StreamingContentLicenseDto streamingcontentlicenseDto,
            UriComponentsBuilder uriBuilder) {

        StreamingContentLicense entity = mapper.toEntity(streamingcontentlicenseDto);
        StreamingContentLicense saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/streamingcontentlicenses/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<StreamingContentLicenseDto>> createAllStreamingContentLicenses(
            @Valid @RequestBody List<StreamingContentLicenseDto> streamingcontentlicenseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<StreamingContentLicense> entities = mapper.toEntityList(streamingcontentlicenseDtoList);
        List<StreamingContentLicense> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/streamingcontentlicenses").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingContentLicenseDto> updateStreamingContentLicense(
            @PathVariable Long id,
            @Valid @RequestBody StreamingContentLicenseDto streamingcontentlicenseDto) {

        StreamingContentLicense entityToUpdate = mapper.toEntity(streamingcontentlicenseDto);
        StreamingContentLicense updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamingContentLicense(@PathVariable Long id) {
        return doDelete(id);
    }
}