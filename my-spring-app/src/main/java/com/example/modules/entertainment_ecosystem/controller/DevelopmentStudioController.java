package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.DevelopmentStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DevelopmentStudioSimpleDto;
import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.mapper.DevelopmentStudioMapper;
import com.example.modules.entertainment_ecosystem.service.DevelopmentStudioService;
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
 * Controller for managing DevelopmentStudio entities.
 */
@RestController
@RequestMapping("/api/developmentstudios")
public class DevelopmentStudioController extends BaseController<DevelopmentStudio, DevelopmentStudioDto, DevelopmentStudioSimpleDto> {

    public DevelopmentStudioController(DevelopmentStudioService developmentstudioService,
                                    DevelopmentStudioMapper developmentstudioMapper) {
        super(developmentstudioService, developmentstudioMapper);
    }

    @GetMapping
    public ResponseEntity<Page<DevelopmentStudioDto>> getAllDevelopmentStudios(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DevelopmentStudioDto>> searchDevelopmentStudios(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(DevelopmentStudio.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevelopmentStudioDto> getDevelopmentStudioById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<DevelopmentStudioDto> createDevelopmentStudio(
            @Valid @RequestBody DevelopmentStudioDto developmentstudioDto,
            UriComponentsBuilder uriBuilder) {

        DevelopmentStudio entity = mapper.toEntity(developmentstudioDto);
        DevelopmentStudio saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/developmentstudios/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DevelopmentStudioDto>> createAllDevelopmentStudios(
            @Valid @RequestBody List<DevelopmentStudioDto> developmentstudioDtoList,
            UriComponentsBuilder uriBuilder) {

        List<DevelopmentStudio> entities = mapper.toEntityList(developmentstudioDtoList);
        List<DevelopmentStudio> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/developmentstudios").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevelopmentStudioDto> updateDevelopmentStudio(
            @PathVariable Long id,
            @Valid @RequestBody DevelopmentStudioDto developmentstudioDto) {

        DevelopmentStudio entityToUpdate = mapper.toEntity(developmentstudioDto);
        DevelopmentStudio updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevelopmentStudio(@PathVariable Long id) {
        return doDelete(id);
    }
}