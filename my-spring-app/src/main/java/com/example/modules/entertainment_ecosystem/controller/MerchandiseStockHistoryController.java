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
        URI location = uriBuilder.path("/api/merchandisestockhistorys/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandisestockhistoryMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseStockHistoryDto> updateMerchandiseStockHistory(
                @PathVariable Long id,
                @RequestBody MerchandiseStockHistoryDto merchandisestockhistoryDto) {

                // Transformer le DTO en entity pour le service
                MerchandiseStockHistory entityToUpdate = merchandisestockhistoryMapper.toEntity(merchandisestockhistoryDto);

                // Appel du service update
                MerchandiseStockHistory updatedEntity = merchandisestockhistoryService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MerchandiseStockHistoryDto updatedDto = merchandisestockhistoryMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMerchandiseStockHistory(@PathVariable Long id) {
                    boolean deleted = merchandisestockhistoryService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}