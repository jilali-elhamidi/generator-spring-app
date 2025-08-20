package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserLoyaltyProgramDto;
import com.example.modules.entertainment_ecosystem.model.UserLoyaltyProgram;
import com.example.modules.entertainment_ecosystem.mapper.UserLoyaltyProgramMapper;
import com.example.modules.entertainment_ecosystem.service.UserLoyaltyProgramService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userloyaltyprograms")
public class UserLoyaltyProgramController {

    private final UserLoyaltyProgramService userloyaltyprogramService;
    private final UserLoyaltyProgramMapper userloyaltyprogramMapper;

    public UserLoyaltyProgramController(UserLoyaltyProgramService userloyaltyprogramService,
                                    UserLoyaltyProgramMapper userloyaltyprogramMapper) {
        this.userloyaltyprogramService = userloyaltyprogramService;
        this.userloyaltyprogramMapper = userloyaltyprogramMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserLoyaltyProgramDto>> getAllUserLoyaltyPrograms() {
        List<UserLoyaltyProgram> entities = userloyaltyprogramService.findAll();
        return ResponseEntity.ok(userloyaltyprogramMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLoyaltyProgramDto> getUserLoyaltyProgramById(@PathVariable Long id) {
        return userloyaltyprogramService.findById(id)
                .map(userloyaltyprogramMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserLoyaltyProgramDto> createUserLoyaltyProgram(
            @Valid @RequestBody UserLoyaltyProgramDto userloyaltyprogramDto,
            UriComponentsBuilder uriBuilder) {

        UserLoyaltyProgram entity = userloyaltyprogramMapper.toEntity(userloyaltyprogramDto);
        UserLoyaltyProgram saved = userloyaltyprogramService.save(entity);

        URI location = uriBuilder
                                .path("/api/userloyaltyprograms/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userloyaltyprogramMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserLoyaltyProgramDto> updateUserLoyaltyProgram(
            @PathVariable Long id,
            @Valid @RequestBody UserLoyaltyProgramDto userloyaltyprogramDto) {


        UserLoyaltyProgram entityToUpdate = userloyaltyprogramMapper.toEntity(userloyaltyprogramDto);
        UserLoyaltyProgram updatedEntity = userloyaltyprogramService.update(id, entityToUpdate);

        return ResponseEntity.ok(userloyaltyprogramMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserLoyaltyProgram(@PathVariable Long id) {
        boolean deleted = userloyaltyprogramService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}