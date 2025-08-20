package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserBlockedListDto;
import com.example.modules.entertainment_ecosystem.model.UserBlockedList;
import com.example.modules.entertainment_ecosystem.mapper.UserBlockedListMapper;
import com.example.modules.entertainment_ecosystem.service.UserBlockedListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userblockedlists")
public class UserBlockedListController {

    private final UserBlockedListService userblockedlistService;
    private final UserBlockedListMapper userblockedlistMapper;

    public UserBlockedListController(UserBlockedListService userblockedlistService,
                                    UserBlockedListMapper userblockedlistMapper) {
        this.userblockedlistService = userblockedlistService;
        this.userblockedlistMapper = userblockedlistMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserBlockedListDto>> getAllUserBlockedLists() {
        List<UserBlockedList> entities = userblockedlistService.findAll();
        return ResponseEntity.ok(userblockedlistMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserBlockedListDto> getUserBlockedListById(@PathVariable Long id) {
        return userblockedlistService.findById(id)
                .map(userblockedlistMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserBlockedListDto> createUserBlockedList(
            @Valid @RequestBody UserBlockedListDto userblockedlistDto,
            UriComponentsBuilder uriBuilder) {

        UserBlockedList entity = userblockedlistMapper.toEntity(userblockedlistDto);
        UserBlockedList saved = userblockedlistService.save(entity);

        URI location = uriBuilder
                                .path("/api/userblockedlists/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userblockedlistMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserBlockedListDto> updateUserBlockedList(
            @PathVariable Long id,
            @Valid @RequestBody UserBlockedListDto userblockedlistDto) {


        UserBlockedList entityToUpdate = userblockedlistMapper.toEntity(userblockedlistDto);
        UserBlockedList updatedEntity = userblockedlistService.update(id, entityToUpdate);

        return ResponseEntity.ok(userblockedlistMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserBlockedList(@PathVariable Long id) {
        boolean deleted = userblockedlistService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}