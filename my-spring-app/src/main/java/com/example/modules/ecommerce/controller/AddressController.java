package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.AddressDto;
import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.mapper.AddressMapper;
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
    private final AddressMapper addressMapper;

    public AddressController(AddressService addressService,
                                    AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresss() {
        List<Address> entities = addressService.findAll();
        return ResponseEntity.ok(addressMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        return addressService.findById(id)
                .map(addressMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(
            @Valid @RequestBody AddressDto addressDto,
            UriComponentsBuilder uriBuilder) {

        Address entity = addressMapper.toEntity(addressDto);
        Address saved = addressService.save(entity);
        URI location = uriBuilder.path("/api/addresss/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(addressMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<AddressDto> updateAddress(
                @PathVariable Long id,
                @RequestBody AddressDto addressDto) {

                // Transformer le DTO en entity pour le service
                Address entityToUpdate = addressMapper.toEntity(addressDto);

                // Appel du service update
                Address updatedEntity = addressService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                AddressDto updatedDto = addressMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
                    boolean deleted = addressService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}