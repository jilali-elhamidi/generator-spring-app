package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserMessageDto;
import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.mapper.UserMessageMapper;
import com.example.modules.entertainment_ecosystem.service.UserMessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usermessages")
public class UserMessageController {

    private final UserMessageService usermessageService;
    private final UserMessageMapper usermessageMapper;

    public UserMessageController(UserMessageService usermessageService,
                                    UserMessageMapper usermessageMapper) {
        this.usermessageService = usermessageService;
        this.usermessageMapper = usermessageMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserMessageDto>> getAllUserMessages() {
        List<UserMessage> entities = usermessageService.findAll();
        return ResponseEntity.ok(usermessageMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMessageDto> getUserMessageById(@PathVariable Long id) {
        return usermessageService.findById(id)
                .map(usermessageMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserMessageDto> createUserMessage(
            @Valid @RequestBody UserMessageDto usermessageDto,
            UriComponentsBuilder uriBuilder) {

        UserMessage entity = usermessageMapper.toEntity(usermessageDto);
        UserMessage saved = usermessageService.save(entity);

        URI location = uriBuilder
                                .path("/api/usermessages/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(usermessageMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserMessageDto> updateUserMessage(
            @PathVariable Long id,
            @Valid @RequestBody UserMessageDto usermessageDto) {


        UserMessage entityToUpdate = usermessageMapper.toEntity(usermessageDto);
        UserMessage updatedEntity = usermessageService.update(id, entityToUpdate);

        return ResponseEntity.ok(usermessageMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserMessage(@PathVariable Long id) {
        boolean deleted = usermessageService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}