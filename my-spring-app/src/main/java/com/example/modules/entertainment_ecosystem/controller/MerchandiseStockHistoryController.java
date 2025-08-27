package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseStockHistoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseStockHistorySimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseStockHistoryMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseStockHistoryService;
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
 * Controller for managing MerchandiseStockHistory entities.
 */
@RestController
@RequestMapping("/api/merchandisestockhistorys")
public class MerchandiseStockHistoryController extends BaseController<MerchandiseStockHistory, MerchandiseStockHistoryDto, MerchandiseStockHistorySimpleDto> {

    public MerchandiseStockHistoryController(MerchandiseStockHistoryService merchandisestockhistoryService,
                                    MerchandiseStockHistoryMapper merchandisestockhistoryMapper) {
        super(merchandisestockhistoryService, merchandisestockhistoryMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseStockHistoryDto>> getAllMerchandiseStockHistorys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseStockHistoryDto>> searchMerchandiseStockHistorys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseStockHistory.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseStockHistoryDto> getMerchandiseStockHistoryById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseStockHistoryDto> createMerchandiseStockHistory(
            @Valid @RequestBody MerchandiseStockHistoryDto merchandisestockhistoryDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseStockHistory entity = mapper.toEntity(merchandisestockhistoryDto);
        MerchandiseStockHistory saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandisestockhistorys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseStockHistoryDto>> createAllMerchandiseStockHistorys(
            @Valid @RequestBody List<MerchandiseStockHistoryDto> merchandisestockhistoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseStockHistory> entities = mapper.toEntityList(merchandisestockhistoryDtoList);
        List<MerchandiseStockHistory> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisestockhistorys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseStockHistoryDto> updateMerchandiseStockHistory(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseStockHistoryDto merchandisestockhistoryDto) {

        MerchandiseStockHistory entityToUpdate = mapper.toEntity(merchandisestockhistoryDto);
        MerchandiseStockHistory updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseStockHistory(@PathVariable Long id) {
        return doDelete(id);
    }
}