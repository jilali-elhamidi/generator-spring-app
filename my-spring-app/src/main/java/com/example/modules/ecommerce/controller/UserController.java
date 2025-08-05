package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

private final UserService userService;

public UserController(UserService userService) {
this.userService = userService;
}

@GetMapping
public ResponseEntity<List<User>> getAllUsers() {
return ResponseEntity.ok(userService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable Long id) {
return userService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<User> createUser(@RequestBody User user) {
return ResponseEntity.ok(userService.save(user));
}

@PutMapping("/{id}")
public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
return ResponseEntity.ok(userService.save(user));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
