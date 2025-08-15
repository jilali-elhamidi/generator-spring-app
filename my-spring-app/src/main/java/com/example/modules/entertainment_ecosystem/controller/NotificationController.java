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
                @RequestBody NotificationDto notificationDto) {

                // Transformer le DTO en entity pour le service
                Notification entityToUpdate = notificationMapper.toEntity(notificationDto);

                // Appel du service update
                Notification updatedEntity = notificationService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                NotificationDto updatedDto = notificationMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
                    boolean deleted = notificationService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}