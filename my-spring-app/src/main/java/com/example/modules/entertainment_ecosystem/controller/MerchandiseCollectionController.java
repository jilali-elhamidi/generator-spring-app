package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseCollectionDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseCollectionMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseCollectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandisecollections")
public class MerchandiseCollectionController {

    private final MerchandiseCollectionService merchandisecollectionService;
    private final MerchandiseCollectionMapper merchandisecollectionMapper;

    public MerchandiseCollectionController(MerchandiseCollectionService merchandisecollectionService,
                                    MerchandiseCollectionMapper merchandisecollectionMapper) {
        this.merchandisecollectionService = merchandisecollectionService;
        this.merchandisecollectionMapper = merchandisecollectionMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseCollectionDto>> getAllMerchandiseCollections() {
        List<MerchandiseCollection> entities = merchandisecollectionService.findAll();
        return ResponseEntity.ok(merchandisecollectionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseCollectionDto> getMerchandiseCollectionById(@PathVariable Long id) {
        return merchandisecollectionService.findById(id)
                .map(merchandisecollectionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseCollectionDto> createMerchandiseCollection(
            @Valid @RequestBody MerchandiseCollectionDto merchandisecollectionDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseCollection entity = merchandisecollectionMapper.toEntity(merchandisecollectionDto);
        MerchandiseCollection saved = merchandisecollectionService.save(entity);
        URI location = uriBuilder.path("/api/merchandisecollections/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandisecollectionMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseCollectionDto> updateMerchandiseCollection(
                @PathVariable Long id,
                @RequestBody MerchandiseCollectionDto merchandisecollectionDto) {

                // Transformer le DTO en entity pour le service
                MerchandiseCollection entityToUpdate = merchandisecollectionMapper.toEntity(merchandisecollectionDto);

                // Appel du service update
                MerchandiseCollection updatedEntity = merchandisecollectionService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MerchandiseCollectionDto updatedDto = merchandisecollectionMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMerchandiseCollection(@PathVariable Long id) {
                    boolean deleted = merchandisecollectionService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}