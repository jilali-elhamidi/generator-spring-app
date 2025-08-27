package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserFollowDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserFollowSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserFollow;
import com.example.modules.entertainment_ecosystem.mapper.UserFollowMapper;
import com.example.modules.entertainment_ecosystem.service.UserFollowService;
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
 * Controller for managing UserFollow entities.
 */
@RestController
@RequestMapping("/api/userfollows")
public class UserFollowController extends BaseController<UserFollow, UserFollowDto, UserFollowSimpleDto> {

    public UserFollowController(UserFollowService userfollowService,
                                    UserFollowMapper userfollowMapper) {
        super(userfollowService, userfollowMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserFollowDto>> getAllUserFollows(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserFollowDto>> searchUserFollows(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserFollow.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFollowDto> getUserFollowById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserFollowDto> createUserFollow(
            @Valid @RequestBody UserFollowDto userfollowDto,
            UriComponentsBuilder uriBuilder) {

        UserFollow entity = mapper.toEntity(userfollowDto);
        UserFollow saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userfollows/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserFollowDto>> createAllUserFollows(
            @Valid @RequestBody List<UserFollowDto> userfollowDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserFollow> entities = mapper.toEntityList(userfollowDtoList);
        List<UserFollow> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userfollows").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserFollowDto> updateUserFollow(
            @PathVariable Long id,
            @Valid @RequestBody UserFollowDto userfollowDto) {

        UserFollow entityToUpdate = mapper.toEntity(userfollowDto);
        UserFollow updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFollow(@PathVariable Long id) {
        return doDelete(id);
    }
}