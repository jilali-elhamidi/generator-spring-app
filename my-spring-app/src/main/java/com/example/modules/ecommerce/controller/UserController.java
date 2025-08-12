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

public UserController(UserService userService, UserMapper userMapper) {
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
            URI location = uriBuilder.path("/api/users/{id}")
            .buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(userMapper.toDto(saved));
            }

            @PutMapping("/{id}")
            public ResponseEntity<UserDto> updateUser(
                @PathVariable Long id,
                @Valid @RequestBody UserDto userDto) {

                try {
                User updatedEntity = userService.update(id, userMapper.toEntity(userDto));
                return ResponseEntity.ok(userMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }

                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
                    userService.deleteById(id);
                    return ResponseEntity.noContent().build();
                    }
                    }