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
        URI location = uriBuilder.path("/api/userwallets/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(userwalletMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<UserWalletDto> updateUserWallet(
                @PathVariable Long id,
                @Valid @RequestBody UserWalletDto userwalletDto) {

                try {
                // Récupérer l'entité existante avec Optional
                UserWallet existing = userwalletService.findById(id)
                .orElseThrow(() -> new RuntimeException("UserWallet not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                userwalletMapper.updateEntityFromDto(userwalletDto, existing);

                // Sauvegarde
                UserWallet updatedEntity = userwalletService.save(existing);

                return ResponseEntity.ok(userwalletMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserWallet(@PathVariable Long id) {
        userwalletService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}