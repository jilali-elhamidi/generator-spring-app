package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/addresss")
public class AddressController {

private final AddressService addressService;

public AddressController(AddressService addressService) {
this.addressService = addressService;
}

@GetMapping
public ResponseEntity<List<Address>> getAllAddresss() {
return ResponseEntity.ok(addressService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
return addressService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<Address> createAddress(@RequestBody Address address) {
return ResponseEntity.ok(addressService.save(address));
}

@PutMapping("/{id}")
public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
return ResponseEntity.ok(addressService.save(address));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
    addressService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
