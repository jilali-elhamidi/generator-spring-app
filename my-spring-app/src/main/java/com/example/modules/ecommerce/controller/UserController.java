package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.UserDto;
import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.mapper.UserMapper;
import com.example.modules.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                                    UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> entities = userService.findAll();
        return ResponseEntity.ok(userMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody UserDto userDto,
            UriComponentsBuilder uriBuilder) {

        User entity = userMapper.toEntity(userDto);
        User saved = userService.save(entity);

        URI location = uriBuilder
                                .path("/api/users/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(userMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserDto>> createAllUsers(
            @Valid @RequestBody List<UserDto> userDtoList,
            UriComponentsBuilder uriBuilder) {

        List<User> entities = userMapper.toEntityList(userDtoList);
        List<User> savedEntities = userService.saveAll(entities);

        URI location = uriBuilder.path("/api/users").build().toUri();

        return ResponseEntity.created(location).body(userMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) {


        User entityToUpdate = userMapper.toEntity(userDto);
        User updatedEntity = userService.update(id, entityToUpdate);

        return ResponseEntity.ok(userMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}