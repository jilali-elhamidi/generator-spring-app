package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserFollowerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserFollowerSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserFollower;
import com.example.modules.entertainment_ecosystem.mapper.UserFollowerMapper;
import com.example.modules.entertainment_ecosystem.service.UserFollowerService;
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
 * Controller for managing UserFollower entities.
 */
@RestController
@RequestMapping("/api/userfollowers")
public class UserFollowerController extends BaseController<UserFollower, UserFollowerDto, UserFollowerSimpleDto> {

    public UserFollowerController(UserFollowerService userfollowerService,
                                    UserFollowerMapper userfollowerMapper) {
        super(userfollowerService, userfollowerMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserFollowerDto>> getAllUserFollowers(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserFollowerDto>> searchUserFollowers(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserFollower.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFollowerDto> getUserFollowerById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserFollowerDto> createUserFollower(
            @Valid @RequestBody UserFollowerDto userfollowerDto,
            UriComponentsBuilder uriBuilder) {

        UserFollower entity = mapper.toEntity(userfollowerDto);
        UserFollower saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userfollowers/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserFollowerDto>> createAllUserFollowers(
            @Valid @RequestBody List<UserFollowerDto> userfollowerDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserFollower> entities = mapper.toEntityList(userfollowerDtoList);
        List<UserFollower> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userfollowers").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserFollowerDto> updateUserFollower(
            @PathVariable Long id,
            @Valid @RequestBody UserFollowerDto userfollowerDto) {

        UserFollower entityToUpdate = mapper.toEntity(userfollowerDto);
        UserFollower updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFollower(@PathVariable Long id) {
        return doDelete(id);
    }
}