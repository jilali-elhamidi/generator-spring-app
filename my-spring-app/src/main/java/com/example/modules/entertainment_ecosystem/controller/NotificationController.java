package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.NotificationDto;
import com.example.modules.entertainment_ecosystem.dtosimple.NotificationSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.mapper.NotificationMapper;
import com.example.modules.entertainment_ecosystem.service.NotificationService;
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
 * Controller for managing Notification entities.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController extends BaseController<Notification, NotificationDto, NotificationSimpleDto> {

    public NotificationController(NotificationService notificationService,
                                    NotificationMapper notificationMapper) {
        super(notificationService, notificationMapper);
    }

    @GetMapping
    public ResponseEntity<Page<NotificationDto>> getAllNotifications(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NotificationDto>> searchNotifications(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Notification.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(
            @Valid @RequestBody NotificationDto notificationDto,
            UriComponentsBuilder uriBuilder) {

        Notification entity = mapper.toEntity(notificationDto);
        Notification saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/notifications/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<NotificationDto>> createAllNotifications(
            @Valid @RequestBody List<NotificationDto> notificationDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Notification> entities = mapper.toEntityList(notificationDtoList);
        List<Notification> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/notifications").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody NotificationDto notificationDto) {

        Notification entityToUpdate = mapper.toEntity(notificationDto);
        Notification updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        return doDelete(id);
    }
}