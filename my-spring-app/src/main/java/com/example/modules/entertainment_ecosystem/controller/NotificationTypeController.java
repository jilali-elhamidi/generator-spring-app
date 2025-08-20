package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.NotificationTypeDto;
import com.example.modules.entertainment_ecosystem.model.NotificationType;
import com.example.modules.entertainment_ecosystem.mapper.NotificationTypeMapper;
import com.example.modules.entertainment_ecosystem.service.NotificationTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notificationtypes")
public class NotificationTypeController {

    private final NotificationTypeService notificationtypeService;
    private final NotificationTypeMapper notificationtypeMapper;

    public NotificationTypeController(NotificationTypeService notificationtypeService,
                                    NotificationTypeMapper notificationtypeMapper) {
        this.notificationtypeService = notificationtypeService;
        this.notificationtypeMapper = notificationtypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<NotificationTypeDto>> getAllNotificationTypes() {
        List<NotificationType> entities = notificationtypeService.findAll();
        return ResponseEntity.ok(notificationtypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationTypeDto> getNotificationTypeById(@PathVariable Long id) {
        return notificationtypeService.findById(id)
                .map(notificationtypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NotificationTypeDto> createNotificationType(
            @Valid @RequestBody NotificationTypeDto notificationtypeDto,
            UriComponentsBuilder uriBuilder) {

        NotificationType entity = notificationtypeMapper.toEntity(notificationtypeDto);
        NotificationType saved = notificationtypeService.save(entity);

        URI location = uriBuilder
                                .path("/api/notificationtypes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(notificationtypeMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationTypeDto> updateNotificationType(
            @PathVariable Long id,
            @Valid @RequestBody NotificationTypeDto notificationtypeDto) {


        NotificationType entityToUpdate = notificationtypeMapper.toEntity(notificationtypeDto);
        NotificationType updatedEntity = notificationtypeService.update(id, entityToUpdate);

        return ResponseEntity.ok(notificationtypeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationType(@PathVariable Long id) {
        boolean deleted = notificationtypeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}