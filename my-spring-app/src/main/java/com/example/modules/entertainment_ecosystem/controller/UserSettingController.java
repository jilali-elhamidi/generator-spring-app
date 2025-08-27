package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserSettingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserSettingSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserSetting;
import com.example.modules.entertainment_ecosystem.mapper.UserSettingMapper;
import com.example.modules.entertainment_ecosystem.service.UserSettingService;
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
 * Controller for managing UserSetting entities.
 */
@RestController
@RequestMapping("/api/usersettings")
public class UserSettingController extends BaseController<UserSetting, UserSettingDto, UserSettingSimpleDto> {

    public UserSettingController(UserSettingService usersettingService,
                                    UserSettingMapper usersettingMapper) {
        super(usersettingService, usersettingMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserSettingDto>> getAllUserSettings(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserSettingDto>> searchUserSettings(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserSetting.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSettingDto> getUserSettingById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserSettingDto> createUserSetting(
            @Valid @RequestBody UserSettingDto usersettingDto,
            UriComponentsBuilder uriBuilder) {

        UserSetting entity = mapper.toEntity(usersettingDto);
        UserSetting saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/usersettings/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserSettingDto>> createAllUserSettings(
            @Valid @RequestBody List<UserSettingDto> usersettingDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserSetting> entities = mapper.toEntityList(usersettingDtoList);
        List<UserSetting> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/usersettings").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSettingDto> updateUserSetting(
            @PathVariable Long id,
            @Valid @RequestBody UserSettingDto usersettingDto) {

        UserSetting entityToUpdate = mapper.toEntity(usersettingDto);
        UserSetting updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSetting(@PathVariable Long id) {
        return doDelete(id);
    }
}