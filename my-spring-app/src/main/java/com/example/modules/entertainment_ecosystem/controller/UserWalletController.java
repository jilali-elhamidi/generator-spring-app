package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserWalletDto;
import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.mapper.UserWalletMapper;
import com.example.modules.entertainment_ecosystem.service.UserWalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/userwallets")
public class UserWalletController {

    private final UserWalletService userwalletService;
    private final UserWalletMapper userwalletMapper;

    public UserWalletController(UserWalletService userwalletService,
                                    UserWalletMapper userwalletMapper) {
        this.userwalletService = userwalletService;
        this.userwalletMapper = userwalletMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserWalletDto>> getAllUserWallets() {
        List<UserWallet> entities = userwalletService.findAll();
        return ResponseEntity.ok(userwalletMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWalletDto> getUserWalletById(@PathVariable Long id) {
        return userwalletService.findById(id)
                .map(userwalletMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserWalletDto> createUserWallet(
            @Valid @RequestBody UserWalletDto userwalletDto,
            UriComponentsBuilder uriBuilder) {

        UserWallet entity = userwalletMapper.toEntity(userwalletDto);
        UserWallet saved = userwalletService.save(entity);

        URI location = uriBuilder
                                .path("/api/userwallets/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userwalletMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserWalletDto>> createAllUserWallets(
            @Valid @RequestBody List<UserWalletDto> userwalletDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserWallet> entities = userwalletMapper.toEntityList(userwalletDtoList);
        List<UserWallet> savedEntities = userwalletService.saveAll(entities);

        URI location = uriBuilder.path("/api/userwallets").build().toUri();

        return ResponseEntity.created(location).body(userwalletMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserWalletDto> updateUserWallet(
            @PathVariable Long id,
            @Valid @RequestBody UserWalletDto userwalletDto) {


        UserWallet entityToUpdate = userwalletMapper.toEntity(userwalletDto);
        UserWallet updatedEntity = userwalletService.update(id, entityToUpdate);

        return ResponseEntity.ok(userwalletMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserWallet(@PathVariable Long id) {
        boolean deleted = userwalletService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}