package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PublisherDto;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.mapper.PublisherMapper;
import com.example.modules.entertainment_ecosystem.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    public PublisherController(PublisherService publisherService,
                                    PublisherMapper publisherMapper) {
        this.publisherService = publisherService;
        this.publisherMapper = publisherMapper;
    }

    @GetMapping
    public ResponseEntity<List<PublisherDto>> getAllPublishers() {
        List<Publisher> entities = publisherService.findAll();
        return ResponseEntity.ok(publisherMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getPublisherById(@PathVariable Long id) {
        return publisherService.findById(id)
                .map(publisherMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PublisherDto> createPublisher(
            @Valid @RequestBody PublisherDto publisherDto,
            UriComponentsBuilder uriBuilder) {

        Publisher entity = publisherMapper.toEntity(publisherDto);
        Publisher saved = publisherService.save(entity);

        URI location = uriBuilder
                                .path("/api/publishers/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(publisherMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PublisherDto>> createAllPublishers(
            @Valid @RequestBody List<PublisherDto> publisherDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Publisher> entities = publisherMapper.toEntityList(publisherDtoList);
        List<Publisher> savedEntities = publisherService.saveAll(entities);

        URI location = uriBuilder.path("/api/publishers").build().toUri();

        return ResponseEntity.created(location).body(publisherMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDto> updatePublisher(
            @PathVariable Long id,
            @Valid @RequestBody PublisherDto publisherDto) {


        Publisher entityToUpdate = publisherMapper.toEntity(publisherDto);
        Publisher updatedEntity = publisherService.update(id, entityToUpdate);

        return ResponseEntity.ok(publisherMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        boolean deleted = publisherService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}