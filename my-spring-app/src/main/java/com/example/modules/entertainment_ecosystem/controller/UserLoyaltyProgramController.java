package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserLoyaltyProgramDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserLoyaltyProgramSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserLoyaltyProgram;
import com.example.modules.entertainment_ecosystem.mapper.UserLoyaltyProgramMapper;
import com.example.modules.entertainment_ecosystem.service.UserLoyaltyProgramService;
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
 * Controller for managing UserLoyaltyProgram entities.
 */
@RestController
@RequestMapping("/api/userloyaltyprograms")
public class UserLoyaltyProgramController extends BaseController<UserLoyaltyProgram, UserLoyaltyProgramDto, UserLoyaltyProgramSimpleDto> {

    public UserLoyaltyProgramController(UserLoyaltyProgramService userloyaltyprogramService,
                                    UserLoyaltyProgramMapper userloyaltyprogramMapper) {
        super(userloyaltyprogramService, userloyaltyprogramMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserLoyaltyProgramDto>> getAllUserLoyaltyPrograms(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserLoyaltyProgramDto>> searchUserLoyaltyPrograms(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserLoyaltyProgram.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLoyaltyProgramDto> getUserLoyaltyProgramById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserLoyaltyProgramDto> createUserLoyaltyProgram(
            @Valid @RequestBody UserLoyaltyProgramDto userloyaltyprogramDto,
            UriComponentsBuilder uriBuilder) {

        UserLoyaltyProgram entity = mapper.toEntity(userloyaltyprogramDto);
        UserLoyaltyProgram saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userloyaltyprograms/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserLoyaltyProgramDto>> createAllUserLoyaltyPrograms(
            @Valid @RequestBody List<UserLoyaltyProgramDto> userloyaltyprogramDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserLoyaltyProgram> entities = mapper.toEntityList(userloyaltyprogramDtoList);
        List<UserLoyaltyProgram> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userloyaltyprograms").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserLoyaltyProgramDto> updateUserLoyaltyProgram(
            @PathVariable Long id,
            @Valid @RequestBody UserLoyaltyProgramDto userloyaltyprogramDto) {

        UserLoyaltyProgram entityToUpdate = mapper.toEntity(userloyaltyprogramDto);
        UserLoyaltyProgram updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserLoyaltyProgram(@PathVariable Long id) {
        return doDelete(id);
    }
}