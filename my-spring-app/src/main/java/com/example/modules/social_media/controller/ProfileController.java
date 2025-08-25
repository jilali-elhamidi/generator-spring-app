package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.ProfileDto;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.mapper.ProfileMapper;
import com.example.modules.social_media.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    public ProfileController(ProfileService profileService,
                                    ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.profileMapper = profileMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        List<Profile> entities = profileService.findAll();
        return ResponseEntity.ok(profileMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable Long id) {
        return profileService.findById(id)
                .map(profileMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(
            @Valid @RequestBody ProfileDto profileDto,
            UriComponentsBuilder uriBuilder) {

        Profile entity = profileMapper.toEntity(profileDto);
        Profile saved = profileService.save(entity);

        URI location = uriBuilder
                                .path("/api/profiles/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(profileMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProfileDto>> createAllProfiles(
            @Valid @RequestBody List<ProfileDto> profileDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Profile> entities = profileMapper.toEntityList(profileDtoList);
        List<Profile> savedEntities = profileService.saveAll(entities);

        URI location = uriBuilder.path("/api/profiles").build().toUri();

        return ResponseEntity.created(location).body(profileMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDto> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody ProfileDto profileDto) {


        Profile entityToUpdate = profileMapper.toEntity(profileDto);
        Profile updatedEntity = profileService.update(id, entityToUpdate);

        return ResponseEntity.ok(profileMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        boolean deleted = profileService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}