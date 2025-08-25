package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseStockHistoryDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseStockHistoryMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseStockHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandisestockhistorys")
public class MerchandiseStockHistoryController {

    private final MerchandiseStockHistoryService merchandisestockhistoryService;
    private final MerchandiseStockHistoryMapper merchandisestockhistoryMapper;

    public MerchandiseStockHistoryController(MerchandiseStockHistoryService merchandisestockhistoryService,
                                    MerchandiseStockHistoryMapper merchandisestockhistoryMapper) {
        this.merchandisestockhistoryService = merchandisestockhistoryService;
        this.merchandisestockhistoryMapper = merchandisestockhistoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseStockHistoryDto>> getAllMerchandiseStockHistorys() {
        List<MerchandiseStockHistory> entities = merchandisestockhistoryService.findAll();
        return ResponseEntity.ok(merchandisestockhistoryMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseStockHistoryDto> getMerchandiseStockHistoryById(@PathVariable Long id) {
        return merchandisestockhistoryService.findById(id)
                .map(merchandisestockhistoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseStockHistoryDto> createMerchandiseStockHistory(
            @Valid @RequestBody MerchandiseStockHistoryDto merchandisestockhistoryDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseStockHistory entity = merchandisestockhistoryMapper.toEntity(merchandisestockhistoryDto);
        MerchandiseStockHistory saved = merchandisestockhistoryService.save(entity);

        URI location = uriBuilder
                                .path("/api/merchandisestockhistorys/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(merchandisestockhistoryMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseStockHistoryDto>> createAllMerchandiseStockHistorys(
            @Valid @RequestBody List<MerchandiseStockHistoryDto> merchandisestockhistoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseStockHistory> entities = merchandisestockhistoryMapper.toEntityList(merchandisestockhistoryDtoList);
        List<MerchandiseStockHistory> savedEntities = merchandisestockhistoryService.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisestockhistorys").build().toUri();

        return ResponseEntity.created(location).body(merchandisestockhistoryMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseStockHistoryDto> updateMerchandiseStockHistory(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseStockHistoryDto merchandisestockhistoryDto) {


        MerchandiseStockHistory entityToUpdate = merchandisestockhistoryMapper.toEntity(merchandisestockhistoryDto);
        MerchandiseStockHistory updatedEntity = merchandisestockhistoryService.update(id, entityToUpdate);

        return ResponseEntity.ok(merchandisestockhistoryMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseStockHistory(@PathVariable Long id) {
        boolean deleted = merchandisestockhistoryService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}