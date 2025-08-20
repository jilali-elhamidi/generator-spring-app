package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingStatusDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingStatus;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseShippingStatusMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseShippingStatusService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandiseshippingstatuss")
public class MerchandiseShippingStatusController {

    private final MerchandiseShippingStatusService merchandiseshippingstatusService;
    private final MerchandiseShippingStatusMapper merchandiseshippingstatusMapper;

    public MerchandiseShippingStatusController(MerchandiseShippingStatusService merchandiseshippingstatusService,
                                    MerchandiseShippingStatusMapper merchandiseshippingstatusMapper) {
        this.merchandiseshippingstatusService = merchandiseshippingstatusService;
        this.merchandiseshippingstatusMapper = merchandiseshippingstatusMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseShippingStatusDto>> getAllMerchandiseShippingStatuss() {
        List<MerchandiseShippingStatus> entities = merchandiseshippingstatusService.findAll();
        return ResponseEntity.ok(merchandiseshippingstatusMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseShippingStatusDto> getMerchandiseShippingStatusById(@PathVariable Long id) {
        return merchandiseshippingstatusService.findById(id)
                .map(merchandiseshippingstatusMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseShippingStatusDto> createMerchandiseShippingStatus(
            @Valid @RequestBody MerchandiseShippingStatusDto merchandiseshippingstatusDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseShippingStatus entity = merchandiseshippingstatusMapper.toEntity(merchandiseshippingstatusDto);
        MerchandiseShippingStatus saved = merchandiseshippingstatusService.save(entity);
        URI location = uriBuilder.path("/api/merchandiseshippingstatuss/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandiseshippingstatusMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseShippingStatusDto> updateMerchandiseShippingStatus(
                @PathVariable Long id,
                @RequestBody MerchandiseShippingStatusDto merchandiseshippingstatusDto) {

                // Transformer le DTO en entity pour le service
                MerchandiseShippingStatus entityToUpdate = merchandiseshippingstatusMapper.toEntity(merchandiseshippingstatusDto);

                // Appel du service update
                MerchandiseShippingStatus updatedEntity = merchandiseshippingstatusService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MerchandiseShippingStatusDto updatedDto = merchandiseshippingstatusMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMerchandiseShippingStatus(@PathVariable Long id) {
                    boolean deleted = merchandiseshippingstatusService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}