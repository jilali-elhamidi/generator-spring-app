package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PublisherDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PublisherSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.mapper.PublisherMapper;
import com.example.modules.entertainment_ecosystem.service.PublisherService;
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
 * Controller for managing Publisher entities.
 */
@RestController
@RequestMapping("/api/publishers")
public class PublisherController extends BaseController<Publisher, PublisherDto, PublisherSimpleDto> {

    public PublisherController(PublisherService publisherService,
                                    PublisherMapper publisherMapper) {
        super(publisherService, publisherMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PublisherDto>> getAllPublishers(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PublisherDto>> searchPublishers(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Publisher.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getPublisherById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PublisherDto> createPublisher(
            @Valid @RequestBody PublisherDto publisherDto,
            UriComponentsBuilder uriBuilder) {

        Publisher entity = mapper.toEntity(publisherDto);
        Publisher saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/publishers/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PublisherDto>> createAllPublishers(
            @Valid @RequestBody List<PublisherDto> publisherDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Publisher> entities = mapper.toEntityList(publisherDtoList);
        List<Publisher> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/publishers").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDto> updatePublisher(
            @PathVariable Long id,
            @Valid @RequestBody PublisherDto publisherDto) {

        Publisher entityToUpdate = mapper.toEntity(publisherDto);
        Publisher updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        return doDelete(id);
    }
}