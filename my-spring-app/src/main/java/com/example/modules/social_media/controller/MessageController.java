package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.MessageDto;
import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.mapper.MessageMapper;
import com.example.modules.social_media.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageController(MessageService messageService,
                                    MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        List<Message> entities = messageService.findAll();
        return ResponseEntity.ok(messageMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable Long id) {
        return messageService.findById(id)
                .map(messageMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(
            @Valid @RequestBody MessageDto messageDto,
            UriComponentsBuilder uriBuilder) {

        Message entity = messageMapper.toEntity(messageDto);
        Message saved = messageService.save(entity);

        URI location = uriBuilder
                                .path("/api/messages/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(messageMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateMessage(
            @PathVariable Long id,
            @Valid @RequestBody MessageDto messageDto) {


        Message entityToUpdate = messageMapper.toEntity(messageDto);
        Message updatedEntity = messageService.update(id, entityToUpdate);

        return ResponseEntity.ok(messageMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        boolean deleted = messageService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}