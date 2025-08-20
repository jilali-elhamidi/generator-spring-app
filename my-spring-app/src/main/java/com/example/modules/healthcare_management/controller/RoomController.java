package com.example.modules.healthcare_management.controller;

import com.example.modules.healthcare_management.dto.RoomDto;
import com.example.modules.healthcare_management.model.Room;
import com.example.modules.healthcare_management.mapper.RoomMapper;
import com.example.modules.healthcare_management.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    public RoomController(RoomService roomService,
                                    RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<Room> entities = roomService.findAll();
        return ResponseEntity.ok(roomMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        return roomService.findById(id)
                .map(roomMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(
            @Valid @RequestBody RoomDto roomDto,
            UriComponentsBuilder uriBuilder) {

        Room entity = roomMapper.toEntity(roomDto);
        Room saved = roomService.save(entity);

        URI location = uriBuilder
                                .path("/api/rooms/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(roomMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomDto roomDto) {


        Room entityToUpdate = roomMapper.toEntity(roomDto);
        Room updatedEntity = roomService.update(id, entityToUpdate);

        return ResponseEntity.ok(roomMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        boolean deleted = roomService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}