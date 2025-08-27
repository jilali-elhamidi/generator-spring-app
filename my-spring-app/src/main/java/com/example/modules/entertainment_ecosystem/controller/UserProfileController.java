package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserProfileDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.mapper.UserProfileMapper;
import com.example.modules.entertainment_ecosystem.service.UserProfileService;
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
 * Controller for managing UserProfile entities.
 */
@RestController
@RequestMapping("/api/userprofiles")
public class UserProfileController extends BaseController<UserProfile, UserProfileDto, UserProfileSimpleDto> {

    public UserProfileController(UserProfileService userprofileService,
                                    UserProfileMapper userprofileMapper) {
        super(userprofileService, userprofileMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserProfileDto>> getAllUserProfiles(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserProfileDto>> searchUserProfiles(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserProfile.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserProfileById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserProfileDto> createUserProfile(
            @Valid @RequestBody UserProfileDto userprofileDto,
            UriComponentsBuilder uriBuilder) {

        UserProfile entity = mapper.toEntity(userprofileDto);
        UserProfile saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userprofiles/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserProfileDto>> createAllUserProfiles(
            @Valid @RequestBody List<UserProfileDto> userprofileDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserProfile> entities = mapper.toEntityList(userprofileDtoList);
        List<UserProfile> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userprofiles").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDto> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserProfileDto userprofileDto) {

        UserProfile entityToUpdate = mapper.toEntity(userprofileDto);
        UserProfile updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        return doDelete(id);
    }
}