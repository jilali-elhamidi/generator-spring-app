package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MessageThreadDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MessageThreadSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.mapper.MessageThreadMapper;
import com.example.modules.entertainment_ecosystem.service.MessageThreadService;
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
 * Controller for managing MessageThread entities.
 */
@RestController
@RequestMapping("/api/messagethreads")
public class MessageThreadController extends BaseController<MessageThread, MessageThreadDto, MessageThreadSimpleDto> {

    public MessageThreadController(MessageThreadService messagethreadService,
                                    MessageThreadMapper messagethreadMapper) {
        super(messagethreadService, messagethreadMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MessageThreadDto>> getAllMessageThreads(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MessageThreadDto>> searchMessageThreads(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MessageThread.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageThreadDto> getMessageThreadById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MessageThreadDto> createMessageThread(
            @Valid @RequestBody MessageThreadDto messagethreadDto,
            UriComponentsBuilder uriBuilder) {

        MessageThread entity = mapper.toEntity(messagethreadDto);
        MessageThread saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/messagethreads/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MessageThreadDto>> createAllMessageThreads(
            @Valid @RequestBody List<MessageThreadDto> messagethreadDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MessageThread> entities = mapper.toEntityList(messagethreadDtoList);
        List<MessageThread> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/messagethreads").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageThreadDto> updateMessageThread(
            @PathVariable Long id,
            @Valid @RequestBody MessageThreadDto messagethreadDto) {

        MessageThread entityToUpdate = mapper.toEntity(messagethreadDto);
        MessageThread updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageThread(@PathVariable Long id) {
        return doDelete(id);
    }
}