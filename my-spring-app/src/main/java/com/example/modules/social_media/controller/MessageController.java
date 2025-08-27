package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.MessageDto;
import com.example.modules.social_media.dtosimple.MessageSimpleDto;
import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.mapper.MessageMapper;
import com.example.modules.social_media.service.MessageService;
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
 * Controller for managing Message entities.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController extends BaseController<Message, MessageDto, MessageSimpleDto> {

    public MessageController(MessageService messageService,
                                    MessageMapper messageMapper) {
        super(messageService, messageMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MessageDto>> getAllMessages(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MessageDto>> searchMessages(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Message.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(
            @Valid @RequestBody MessageDto messageDto,
            UriComponentsBuilder uriBuilder) {

        Message entity = mapper.toEntity(messageDto);
        Message saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/messages/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MessageDto>> createAllMessages(
            @Valid @RequestBody List<MessageDto> messageDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Message> entities = mapper.toEntityList(messageDtoList);
        List<Message> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/messages").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateMessage(
            @PathVariable Long id,
            @Valid @RequestBody MessageDto messageDto) {

        Message entityToUpdate = mapper.toEntity(messageDto);
        Message updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        return doDelete(id);
    }
}