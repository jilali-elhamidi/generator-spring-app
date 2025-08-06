package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
public ResponseEntity<Address> createAddress(
@Valid @RequestBody Address address,
UriComponentsBuilder uriBuilder) {

Address saved = addressService.save(address);
URI location = uriBuilder.path("/api/addresss/{id}")
.buildAndExpand(saved.getId()).toUri();

return ResponseEntity.created(location).body(saved);
}

@PutMapping("/{id}")
public ResponseEntity<Address> updateAddress(
@PathVariable Long id,
@Valid @RequestBody Address addressRequest) {

try {
Address updated = addressService.update(id, addressRequest);
return ResponseEntity.ok(updated);
} catch (RuntimeException e) {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
    addressService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
