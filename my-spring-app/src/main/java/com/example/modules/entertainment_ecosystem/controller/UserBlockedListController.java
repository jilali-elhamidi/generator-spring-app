package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserBlockedListDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserBlockedListSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserBlockedList;
import com.example.modules.entertainment_ecosystem.mapper.UserBlockedListMapper;
import com.example.modules.entertainment_ecosystem.service.UserBlockedListService;
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
 * Controller for managing UserBlockedList entities.
 */
@RestController
@RequestMapping("/api/userblockedlists")
public class UserBlockedListController extends BaseController<UserBlockedList, UserBlockedListDto, UserBlockedListSimpleDto> {

    public UserBlockedListController(UserBlockedListService userblockedlistService,
                                    UserBlockedListMapper userblockedlistMapper) {
        super(userblockedlistService, userblockedlistMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserBlockedListDto>> getAllUserBlockedLists(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserBlockedListDto>> searchUserBlockedLists(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserBlockedList.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserBlockedListDto> getUserBlockedListById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserBlockedListDto> createUserBlockedList(
            @Valid @RequestBody UserBlockedListDto userblockedlistDto,
            UriComponentsBuilder uriBuilder) {

        UserBlockedList entity = mapper.toEntity(userblockedlistDto);
        UserBlockedList saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userblockedlists/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserBlockedListDto>> createAllUserBlockedLists(
            @Valid @RequestBody List<UserBlockedListDto> userblockedlistDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserBlockedList> entities = mapper.toEntityList(userblockedlistDtoList);
        List<UserBlockedList> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userblockedlists").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserBlockedListDto> updateUserBlockedList(
            @PathVariable Long id,
            @Valid @RequestBody UserBlockedListDto userblockedlistDto) {

        UserBlockedList entityToUpdate = mapper.toEntity(userblockedlistDto);
        UserBlockedList updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserBlockedList(@PathVariable Long id) {
        return doDelete(id);
    }
}