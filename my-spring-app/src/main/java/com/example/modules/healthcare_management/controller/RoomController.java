package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.RoomDto;
import com.example.modules.healthcare_management.dtosimple.RoomSimpleDto;
import com.example.modules.healthcare_management.model.Room;
import com.example.modules.healthcare_management.mapper.RoomMapper;
import com.example.modules.healthcare_management.service.RoomService;
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
 * Controller for managing Room entities.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomController extends BaseController<Room, RoomDto, RoomSimpleDto> {

    public RoomController(RoomService roomService,
                                    RoomMapper roomMapper) {
        super(roomService, roomMapper);
    }

    @GetMapping
    public ResponseEntity<Page<RoomDto>> getAllRooms(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RoomDto>> searchRooms(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Room.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(
            @Valid @RequestBody RoomDto roomDto,
            UriComponentsBuilder uriBuilder) {

        Room entity = mapper.toEntity(roomDto);
        Room saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/rooms/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<RoomDto>> createAllRooms(
            @Valid @RequestBody List<RoomDto> roomDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Room> entities = mapper.toEntityList(roomDtoList);
        List<Room> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/rooms").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomDto roomDto) {

        Room entityToUpdate = mapper.toEntity(roomDto);
        Room updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        return doDelete(id);
    }
}