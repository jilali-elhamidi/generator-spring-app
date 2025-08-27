package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingStatusDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingStatusSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingStatus;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseShippingStatusMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseShippingStatusService;
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
 * Controller for managing MerchandiseShippingStatus entities.
 */
@RestController
@RequestMapping("/api/merchandiseshippingstatuss")
public class MerchandiseShippingStatusController extends BaseController<MerchandiseShippingStatus, MerchandiseShippingStatusDto, MerchandiseShippingStatusSimpleDto> {

    public MerchandiseShippingStatusController(MerchandiseShippingStatusService merchandiseshippingstatusService,
                                    MerchandiseShippingStatusMapper merchandiseshippingstatusMapper) {
        super(merchandiseshippingstatusService, merchandiseshippingstatusMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseShippingStatusDto>> getAllMerchandiseShippingStatuss(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseShippingStatusDto>> searchMerchandiseShippingStatuss(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseShippingStatus.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseShippingStatusDto> getMerchandiseShippingStatusById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseShippingStatusDto> createMerchandiseShippingStatus(
            @Valid @RequestBody MerchandiseShippingStatusDto merchandiseshippingstatusDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseShippingStatus entity = mapper.toEntity(merchandiseshippingstatusDto);
        MerchandiseShippingStatus saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandiseshippingstatuss/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseShippingStatusDto>> createAllMerchandiseShippingStatuss(
            @Valid @RequestBody List<MerchandiseShippingStatusDto> merchandiseshippingstatusDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseShippingStatus> entities = mapper.toEntityList(merchandiseshippingstatusDtoList);
        List<MerchandiseShippingStatus> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandiseshippingstatuss").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseShippingStatusDto> updateMerchandiseShippingStatus(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseShippingStatusDto merchandiseshippingstatusDto) {

        MerchandiseShippingStatus entityToUpdate = mapper.toEntity(merchandiseshippingstatusDto);
        MerchandiseShippingStatus updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseShippingStatus(@PathVariable Long id) {
        return doDelete(id);
    }
}