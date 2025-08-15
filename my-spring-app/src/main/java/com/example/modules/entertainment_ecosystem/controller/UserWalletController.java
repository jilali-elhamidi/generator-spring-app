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
                @RequestBody UserWalletDto userwalletDto) {

                // Transformer le DTO en entity pour le service
                UserWallet entityToUpdate = userwalletMapper.toEntity(userwalletDto);

                // Appel du service update
                UserWallet updatedEntity = userwalletService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                UserWalletDto updatedDto = userwalletMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserWallet(@PathVariable Long id) {
        userwalletService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}