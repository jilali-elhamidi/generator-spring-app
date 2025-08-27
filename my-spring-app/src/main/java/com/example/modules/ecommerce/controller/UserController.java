package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.UserDto;
import com.example.modules.ecommerce.dtosimple.UserSimpleDto;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.mapper.UserMapper;
import com.example.modules.ecommerce.service.UserService;
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
 * Controller for managing User entities.
 */
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController<User, UserDto, UserSimpleDto> {

    public UserController(UserService userService,
                                    UserMapper userMapper) {
        super(userService, userMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserDto>> searchUsers(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(User.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody UserDto userDto,
            UriComponentsBuilder uriBuilder) {

        User entity = mapper.toEntity(userDto);
        User saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/users/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserDto>> createAllUsers(
            @Valid @RequestBody List<UserDto> userDtoList,
            UriComponentsBuilder uriBuilder) {

        List<User> entities = mapper.toEntityList(userDtoList);
        List<User> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/users").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) {

        User entityToUpdate = mapper.toEntity(userDto);
        User updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return doDelete(id);
    }
}