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
        URI location = uriBuilder.path("/api/productioncompanys/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(productioncompanyMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ProductionCompanyDto> updateProductionCompany(
                @PathVariable Long id,
                @Valid @RequestBody ProductionCompanyDto productioncompanyDto) {

                try {
                // Récupérer l'entité existante avec Optional
                ProductionCompany existing = productioncompanyService.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                productioncompanyMapper.updateEntityFromDto(productioncompanyDto, existing);

                // Sauvegarde
                ProductionCompany updatedEntity = productioncompanyService.save(existing);

                return ResponseEntity.ok(productioncompanyMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductionCompany(@PathVariable Long id) {
        productioncompanyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}