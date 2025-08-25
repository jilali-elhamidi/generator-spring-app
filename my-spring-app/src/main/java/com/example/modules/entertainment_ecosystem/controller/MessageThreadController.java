package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MessageThreadDto;
import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.mapper.MessageThreadMapper;
import com.example.modules.entertainment_ecosystem.service.MessageThreadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/messagethreads")
public class MessageThreadController {

    private final MessageThreadService messagethreadService;
    private final MessageThreadMapper messagethreadMapper;

    public MessageThreadController(MessageThreadService messagethreadService,
                                    MessageThreadMapper messagethreadMapper) {
        this.messagethreadService = messagethreadService;
        this.messagethreadMapper = messagethreadMapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageThreadDto>> getAllMessageThreads() {
        List<MessageThread> entities = messagethreadService.findAll();
        return ResponseEntity.ok(messagethreadMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageThreadDto> getMessageThreadById(@PathVariable Long id) {
        return messagethreadService.findById(id)
                .map(messagethreadMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MessageThreadDto> createMessageThread(
            @Valid @RequestBody MessageThreadDto messagethreadDto,
            UriComponentsBuilder uriBuilder) {

        MessageThread entity = messagethreadMapper.toEntity(messagethreadDto);
        MessageThread saved = messagethreadService.save(entity);

        URI location = uriBuilder
                                .path("/api/messagethreads/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(messagethreadMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MessageThreadDto>> createAllMessageThreads(
            @Valid @RequestBody List<MessageThreadDto> messagethreadDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MessageThread> entities = messagethreadMapper.toEntityList(messagethreadDtoList);
        List<MessageThread> savedEntities = messagethreadService.saveAll(entities);

        URI location = uriBuilder.path("/api/messagethreads").build().toUri();

        return ResponseEntity.created(location).body(messagethreadMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageThreadDto> updateMessageThread(
            @PathVariable Long id,
            @Valid @RequestBody MessageThreadDto messagethreadDto) {


        MessageThread entityToUpdate = messagethreadMapper.toEntity(messagethreadDto);
        MessageThread updatedEntity = messagethreadService.update(id, entityToUpdate);

        return ResponseEntity.ok(messagethreadMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageThread(@PathVariable Long id) {
        boolean deleted = messagethreadService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}