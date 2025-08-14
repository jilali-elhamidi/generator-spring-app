package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.NotificationDto;
import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.mapper.NotificationMapper;
import com.example.modules.entertainment_ecosystem.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    public NotificationController(NotificationService notificationService,
                                    NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<Notification> entities = notificationService.findAll();
        return ResponseEntity.ok(notificationMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        return notificationService.findById(id)
                .map(notificationMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(
            @Valid @RequestBody NotificationDto notificationDto,
            UriComponentsBuilder uriBuilder) {

        Notification entity = notificationMapper.toEntity(notificationDto);
        Notification saved = notificationService.save(entity);
        URI location = uriBuilder.path("/api/notifications/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(notificationMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<NotificationDto> updateNotification(
                @PathVariable Long id,
                @Valid @RequestBody NotificationDto notificationDto) {

                try {
                // Récupérer l'entité existante avec Optional
                Notification existing = notificationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                notificationMapper.updateEntityFromDto(notificationDto, existing);

                // Sauvegarde
                Notification updatedEntity = notificationService.save(existing);

                return ResponseEntity.ok(notificationMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}