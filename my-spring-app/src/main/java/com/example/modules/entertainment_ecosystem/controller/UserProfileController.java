package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserProfileDto;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.mapper.UserProfileMapper;
import com.example.modules.entertainment_ecosystem.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userprofiles")
public class UserProfileController {

    private final UserProfileService userprofileService;
    private final UserProfileMapper userprofileMapper;

    public UserProfileController(UserProfileService userprofileService,
                                    UserProfileMapper userprofileMapper) {
        this.userprofileService = userprofileService;
        this.userprofileMapper = userprofileMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDto>> getAllUserProfiles() {
        List<UserProfile> entities = userprofileService.findAll();
        return ResponseEntity.ok(userprofileMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserProfileById(@PathVariable Long id) {
        return userprofileService.findById(id)
                .map(userprofileMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserProfileDto> createUserProfile(
            @Valid @RequestBody UserProfileDto userprofileDto,
            UriComponentsBuilder uriBuilder) {

        UserProfile entity = userprofileMapper.toEntity(userprofileDto);
        UserProfile saved = userprofileService.save(entity);

        URI location = uriBuilder
                                .path("/api/userprofiles/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userprofileMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDto> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserProfileDto userprofileDto) {


        UserProfile entityToUpdate = userprofileMapper.toEntity(userprofileDto);
        UserProfile updatedEntity = userprofileService.update(id, entityToUpdate);

        return ResponseEntity.ok(userprofileMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        boolean deleted = userprofileService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}