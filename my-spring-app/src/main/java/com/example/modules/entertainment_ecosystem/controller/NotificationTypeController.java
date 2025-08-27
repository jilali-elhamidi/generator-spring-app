package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.NotificationTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.NotificationTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.NotificationType;
import com.example.modules.entertainment_ecosystem.mapper.NotificationTypeMapper;
import com.example.modules.entertainment_ecosystem.service.NotificationTypeService;
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
 * Controller for managing NotificationType entities.
 */
@RestController
@RequestMapping("/api/notificationtypes")
public class NotificationTypeController extends BaseController<NotificationType, NotificationTypeDto, NotificationTypeSimpleDto> {

    public NotificationTypeController(NotificationTypeService notificationtypeService,
                                    NotificationTypeMapper notificationtypeMapper) {
        super(notificationtypeService, notificationtypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<NotificationTypeDto>> getAllNotificationTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NotificationTypeDto>> searchNotificationTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(NotificationType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationTypeDto> getNotificationTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<NotificationTypeDto> createNotificationType(
            @Valid @RequestBody NotificationTypeDto notificationtypeDto,
            UriComponentsBuilder uriBuilder) {

        NotificationType entity = mapper.toEntity(notificationtypeDto);
        NotificationType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/notificationtypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<NotificationTypeDto>> createAllNotificationTypes(
            @Valid @RequestBody List<NotificationTypeDto> notificationtypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<NotificationType> entities = mapper.toEntityList(notificationtypeDtoList);
        List<NotificationType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/notificationtypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationTypeDto> updateNotificationType(
            @PathVariable Long id,
            @Valid @RequestBody NotificationTypeDto notificationtypeDto) {

        NotificationType entityToUpdate = mapper.toEntity(notificationtypeDto);
        NotificationType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationType(@PathVariable Long id) {
        return doDelete(id);
    }
}