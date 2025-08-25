package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ProductionCompanyDto;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.mapper.ProductionCompanyMapper;
import com.example.modules.entertainment_ecosystem.service.ProductionCompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productioncompanys")
public class ProductionCompanyController {

    private final ProductionCompanyService productioncompanyService;
    private final ProductionCompanyMapper productioncompanyMapper;

    public ProductionCompanyController(ProductionCompanyService productioncompanyService,
                                    ProductionCompanyMapper productioncompanyMapper) {
        this.productioncompanyService = productioncompanyService;
        this.productioncompanyMapper = productioncompanyMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductionCompanyDto>> getAllProductionCompanys() {
        List<ProductionCompany> entities = productioncompanyService.findAll();
        return ResponseEntity.ok(productioncompanyMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionCompanyDto> getProductionCompanyById(@PathVariable Long id) {
        return productioncompanyService.findById(id)
                .map(productioncompanyMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductionCompanyDto> createProductionCompany(
            @Valid @RequestBody ProductionCompanyDto productioncompanyDto,
            UriComponentsBuilder uriBuilder) {

        ProductionCompany entity = productioncompanyMapper.toEntity(productioncompanyDto);
        ProductionCompany saved = productioncompanyService.save(entity);

        URI location = uriBuilder
                                .path("/api/productioncompanys/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(productioncompanyMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProductionCompanyDto>> createAllProductionCompanys(
            @Valid @RequestBody List<ProductionCompanyDto> productioncompanyDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ProductionCompany> entities = productioncompanyMapper.toEntityList(productioncompanyDtoList);
        List<ProductionCompany> savedEntities = productioncompanyService.saveAll(entities);

        URI location = uriBuilder.path("/api/productioncompanys").build().toUri();

        return ResponseEntity.created(location).body(productioncompanyMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductionCompanyDto> updateProductionCompany(
            @PathVariable Long id,
            @Valid @RequestBody ProductionCompanyDto productioncompanyDto) {


        ProductionCompany entityToUpdate = productioncompanyMapper.toEntity(productioncompanyDto);
        ProductionCompany updatedEntity = productioncompanyService.update(id, entityToUpdate);

        return ResponseEntity.ok(productioncompanyMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductionCompany(@PathVariable Long id) {
        boolean deleted = productioncompanyService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}