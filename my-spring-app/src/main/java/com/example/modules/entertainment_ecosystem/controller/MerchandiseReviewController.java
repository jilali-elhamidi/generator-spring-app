package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseReviewDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseReviewMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandisereviews")
public class MerchandiseReviewController {

    private final MerchandiseReviewService merchandisereviewService;
    private final MerchandiseReviewMapper merchandisereviewMapper;

    public MerchandiseReviewController(MerchandiseReviewService merchandisereviewService,
                                    MerchandiseReviewMapper merchandisereviewMapper) {
        this.merchandisereviewService = merchandisereviewService;
        this.merchandisereviewMapper = merchandisereviewMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseReviewDto>> getAllMerchandiseReviews() {
        List<MerchandiseReview> entities = merchandisereviewService.findAll();
        return ResponseEntity.ok(merchandisereviewMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseReviewDto> getMerchandiseReviewById(@PathVariable Long id) {
        return merchandisereviewService.findById(id)
                .map(merchandisereviewMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseReviewDto> createMerchandiseReview(
            @Valid @RequestBody MerchandiseReviewDto merchandisereviewDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseReview entity = merchandisereviewMapper.toEntity(merchandisereviewDto);
        MerchandiseReview saved = merchandisereviewService.save(entity);
        URI location = uriBuilder.path("/api/merchandisereviews/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandisereviewMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseReviewDto> updateMerchandiseReview(
                @PathVariable Long id,
                @RequestBody MerchandiseReviewDto merchandisereviewDto) {

                // Transformer le DTO en entity pour le service
                MerchandiseReview entityToUpdate = merchandisereviewMapper.toEntity(merchandisereviewDto);

                // Appel du service update
                MerchandiseReview updatedEntity = merchandisereviewService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MerchandiseReviewDto updatedDto = merchandisereviewMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMerchandiseReview(@PathVariable Long id) {
                    boolean deleted = merchandisereviewService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}