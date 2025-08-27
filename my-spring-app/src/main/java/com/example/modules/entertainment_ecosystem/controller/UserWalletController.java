package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserWalletDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserWalletSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserWallet;
import com.example.modules.entertainment_ecosystem.mapper.UserWalletMapper;
import com.example.modules.entertainment_ecosystem.service.UserWalletService;
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
 * Controller for managing UserWallet entities.
 */
@RestController
@RequestMapping("/api/userwallets")
public class UserWalletController extends BaseController<UserWallet, UserWalletDto, UserWalletSimpleDto> {

    public UserWalletController(UserWalletService userwalletService,
                                    UserWalletMapper userwalletMapper) {
        super(userwalletService, userwalletMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserWalletDto>> getAllUserWallets(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserWalletDto>> searchUserWallets(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserWallet.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWalletDto> getUserWalletById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserWalletDto> createUserWallet(
            @Valid @RequestBody UserWalletDto userwalletDto,
            UriComponentsBuilder uriBuilder) {

        UserWallet entity = mapper.toEntity(userwalletDto);
        UserWallet saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userwallets/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserWalletDto>> createAllUserWallets(
            @Valid @RequestBody List<UserWalletDto> userwalletDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserWallet> entities = mapper.toEntityList(userwalletDtoList);
        List<UserWallet> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userwallets").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserWalletDto> updateUserWallet(
            @PathVariable Long id,
            @Valid @RequestBody UserWalletDto userwalletDto) {

        UserWallet entityToUpdate = mapper.toEntity(userwalletDto);
        UserWallet updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserWallet(@PathVariable Long id) {
        return doDelete(id);
    }
}