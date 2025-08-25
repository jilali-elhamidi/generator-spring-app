package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.StreamingServiceDto;
import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.mapper.StreamingServiceMapper;
import com.example.modules.entertainment_ecosystem.service.StreamingServiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/streamingservices")
public class StreamingServiceController {

    private final StreamingServiceService streamingserviceService;
    private final StreamingServiceMapper streamingserviceMapper;

    public StreamingServiceController(StreamingServiceService streamingserviceService,
                                    StreamingServiceMapper streamingserviceMapper) {
        this.streamingserviceService = streamingserviceService;
        this.streamingserviceMapper = streamingserviceMapper;
    }

    @GetMapping
    public ResponseEntity<List<StreamingServiceDto>> getAllStreamingServices() {
        List<StreamingService> entities = streamingserviceService.findAll();
        return ResponseEntity.ok(streamingserviceMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingServiceDto> getStreamingServiceById(@PathVariable Long id) {
        return streamingserviceService.findById(id)
                .map(streamingserviceMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StreamingServiceDto> createStreamingService(
            @Valid @RequestBody StreamingServiceDto streamingserviceDto,
            UriComponentsBuilder uriBuilder) {

        StreamingService entity = streamingserviceMapper.toEntity(streamingserviceDto);
        StreamingService saved = streamingserviceService.save(entity);

        URI location = uriBuilder
                                .path("/api/streamingservices/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(streamingserviceMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<StreamingServiceDto>> createAllStreamingServices(
            @Valid @RequestBody List<StreamingServiceDto> streamingserviceDtoList,
            UriComponentsBuilder uriBuilder) {

        List<StreamingService> entities = streamingserviceMapper.toEntityList(streamingserviceDtoList);
        List<StreamingService> savedEntities = streamingserviceService.saveAll(entities);

        URI location = uriBuilder.path("/api/streamingservices").build().toUri();

        return ResponseEntity.created(location).body(streamingserviceMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingServiceDto> updateStreamingService(
            @PathVariable Long id,
            @Valid @RequestBody StreamingServiceDto streamingserviceDto) {


        StreamingService entityToUpdate = streamingserviceMapper.toEntity(streamingserviceDto);
        StreamingService updatedEntity = streamingserviceService.update(id, entityToUpdate);

        return ResponseEntity.ok(streamingserviceMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamingService(@PathVariable Long id) {
        boolean deleted = streamingserviceService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}