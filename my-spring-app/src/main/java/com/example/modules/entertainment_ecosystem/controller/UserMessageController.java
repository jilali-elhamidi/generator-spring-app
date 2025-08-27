package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserMessageDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserMessageSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.mapper.UserMessageMapper;
import com.example.modules.entertainment_ecosystem.service.UserMessageService;
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
 * Controller for managing UserMessage entities.
 */
@RestController
@RequestMapping("/api/usermessages")
public class UserMessageController extends BaseController<UserMessage, UserMessageDto, UserMessageSimpleDto> {

    public UserMessageController(UserMessageService usermessageService,
                                    UserMessageMapper usermessageMapper) {
        super(usermessageService, usermessageMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserMessageDto>> getAllUserMessages(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserMessageDto>> searchUserMessages(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserMessage.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMessageDto> getUserMessageById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserMessageDto> createUserMessage(
            @Valid @RequestBody UserMessageDto usermessageDto,
            UriComponentsBuilder uriBuilder) {

        UserMessage entity = mapper.toEntity(usermessageDto);
        UserMessage saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/usermessages/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserMessageDto>> createAllUserMessages(
            @Valid @RequestBody List<UserMessageDto> usermessageDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserMessage> entities = mapper.toEntityList(usermessageDtoList);
        List<UserMessage> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/usermessages").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserMessageDto> updateUserMessage(
            @PathVariable Long id,
            @Valid @RequestBody UserMessageDto usermessageDto) {

        UserMessage entityToUpdate = mapper.toEntity(usermessageDto);
        UserMessage updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserMessage(@PathVariable Long id) {
        return doDelete(id);
    }
}