package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ProductionCompanyDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ProductionCompanySimpleDto;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.mapper.ProductionCompanyMapper;
import com.example.modules.entertainment_ecosystem.service.ProductionCompanyService;
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
 * Controller for managing ProductionCompany entities.
 */
@RestController
@RequestMapping("/api/productioncompanys")
public class ProductionCompanyController extends BaseController<ProductionCompany, ProductionCompanyDto, ProductionCompanySimpleDto> {

    public ProductionCompanyController(ProductionCompanyService productioncompanyService,
                                    ProductionCompanyMapper productioncompanyMapper) {
        super(productioncompanyService, productioncompanyMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ProductionCompanyDto>> getAllProductionCompanys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductionCompanyDto>> searchProductionCompanys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ProductionCompany.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionCompanyDto> getProductionCompanyById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ProductionCompanyDto> createProductionCompany(
            @Valid @RequestBody ProductionCompanyDto productioncompanyDto,
            UriComponentsBuilder uriBuilder) {

        ProductionCompany entity = mapper.toEntity(productioncompanyDto);
        ProductionCompany saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/productioncompanys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProductionCompanyDto>> createAllProductionCompanys(
            @Valid @RequestBody List<ProductionCompanyDto> productioncompanyDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ProductionCompany> entities = mapper.toEntityList(productioncompanyDtoList);
        List<ProductionCompany> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/productioncompanys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductionCompanyDto> updateProductionCompany(
            @PathVariable Long id,
            @Valid @RequestBody ProductionCompanyDto productioncompanyDto) {

        ProductionCompany entityToUpdate = mapper.toEntity(productioncompanyDto);
        ProductionCompany updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductionCompany(@PathVariable Long id) {
        return doDelete(id);
    }
}