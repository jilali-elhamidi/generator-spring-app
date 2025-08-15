package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserSettingDto;
import com.example.modules.entertainment_ecosystem.model.UserSetting;
import com.example.modules.entertainment_ecosystem.mapper.UserSettingMapper;
import com.example.modules.entertainment_ecosystem.service.UserSettingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usersettings")
public class UserSettingController {

    private final UserSettingService usersettingService;
    private final UserSettingMapper usersettingMapper;

    public UserSettingController(UserSettingService usersettingService,
                                    UserSettingMapper usersettingMapper) {
        this.usersettingService = usersettingService;
        this.usersettingMapper = usersettingMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserSettingDto>> getAllUserSettings() {
        List<UserSetting> entities = usersettingService.findAll();
        return ResponseEntity.ok(usersettingMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSettingDto> getUserSettingById(@PathVariable Long id) {
        return usersettingService.findById(id)
                .map(usersettingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserSettingDto> createUserSetting(
            @Valid @RequestBody UserSettingDto usersettingDto,
            UriComponentsBuilder uriBuilder) {

        UserSetting entity = usersettingMapper.toEntity(usersettingDto);
        UserSetting saved = usersettingService.save(entity);
        URI location = uriBuilder.path("/api/usersettings/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(usersettingMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserSettingDto> updateUserSetting(
                @PathVariable Long id,
                @RequestBody UserSettingDto usersettingDto) {

                // Transformer le DTO en entity pour le service
                UserSetting entityToUpdate = usersettingMapper.toEntity(usersettingDto);

                // Appel du service update
                UserSetting updatedEntity = usersettingService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserSettingDto updatedDto = usersettingMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSetting(@PathVariable Long id) {
        usersettingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}