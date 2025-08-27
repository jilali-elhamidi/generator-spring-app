package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.AddressDto;
import com.example.modules.ecommerce.dtosimple.AddressSimpleDto;
import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.mapper.AddressMapper;
import com.example.modules.ecommerce.service.AddressService;
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
 * Controller for managing Address entities.
 */
@RestController
@RequestMapping("/api/addresss")
public class AddressController extends BaseController<Address, AddressDto, AddressSimpleDto> {

    public AddressController(AddressService addressService,
                                    AddressMapper addressMapper) {
        super(addressService, addressMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AddressDto>> getAllAddresss(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AddressDto>> searchAddresss(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Address.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(
            @Valid @RequestBody AddressDto addressDto,
            UriComponentsBuilder uriBuilder) {

        Address entity = mapper.toEntity(addressDto);
        Address saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/addresss/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AddressDto>> createAllAddresss(
            @Valid @RequestBody List<AddressDto> addressDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Address> entities = mapper.toEntityList(addressDtoList);
        List<Address> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/addresss").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressDto addressDto) {

        Address entityToUpdate = mapper.toEntity(addressDto);
        Address updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        return doDelete(id);
    }
}