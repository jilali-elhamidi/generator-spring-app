package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.StreamingServiceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingServiceSimpleDto;
import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.mapper.StreamingServiceMapper;
import com.example.modules.entertainment_ecosystem.service.StreamingServiceService;
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
 * Controller for managing StreamingService entities.
 */
@RestController
@RequestMapping("/api/streamingservices")
public class StreamingServiceController extends BaseController<StreamingService, StreamingServiceDto, StreamingServiceSimpleDto> {

    public StreamingServiceController(StreamingServiceService streamingserviceService,
                                    StreamingServiceMapper streamingserviceMapper) {
        super(streamingserviceService, streamingserviceMapper);
    }

    @GetMapping
    public ResponseEntity<Page<StreamingServiceDto>> getAllStreamingServices(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StreamingServiceDto>> searchStreamingServices(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(StreamingService.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingServiceDto> getStreamingServiceById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<StreamingServiceDto> createStreamingService(
            @Valid @RequestBody StreamingServiceDto streamingserviceDto,
            UriComponentsBuilder uriBuilder) {

        StreamingService entity = mapper.toEntity(streamingserviceDto);
        StreamingService saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/streamingservices/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<StreamingServiceDto>> createAllStreamingServices(
            @Valid @RequestBody List<StreamingServiceDto> streamingserviceDtoList,
            UriComponentsBuilder uriBuilder) {

        List<StreamingService> entities = mapper.toEntityList(streamingserviceDtoList);
        List<StreamingService> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/streamingservices").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingServiceDto> updateStreamingService(
            @PathVariable Long id,
            @Valid @RequestBody StreamingServiceDto streamingserviceDto) {

        StreamingService entityToUpdate = mapper.toEntity(streamingserviceDto);
        StreamingService updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamingService(@PathVariable Long id) {
        return doDelete(id);
    }
}