package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.ProfileDto;
import com.example.modules.social_media.dtosimple.ProfileSimpleDto;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.mapper.ProfileMapper;
import com.example.modules.social_media.service.ProfileService;
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
 * Controller for managing Profile entities.
 */
@RestController
@RequestMapping("/api/profiles")
public class ProfileController extends BaseController<Profile, ProfileDto, ProfileSimpleDto> {

    public ProfileController(ProfileService profileService,
                                    ProfileMapper profileMapper) {
        super(profileService, profileMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ProfileDto>> getAllProfiles(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProfileDto>> searchProfiles(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Profile.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(
            @Valid @RequestBody ProfileDto profileDto,
            UriComponentsBuilder uriBuilder) {

        Profile entity = mapper.toEntity(profileDto);
        Profile saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/profiles/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProfileDto>> createAllProfiles(
            @Valid @RequestBody List<ProfileDto> profileDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Profile> entities = mapper.toEntityList(profileDtoList);
        List<Profile> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/profiles").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDto> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody ProfileDto profileDto) {

        Profile entityToUpdate = mapper.toEntity(profileDto);
        Profile updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        return doDelete(id);
    }
}