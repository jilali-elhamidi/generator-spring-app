package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseReviewSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseReviewMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseReviewService;
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
 * Controller for managing MerchandiseReview entities.
 */
@RestController
@RequestMapping("/api/merchandisereviews")
public class MerchandiseReviewController extends BaseController<MerchandiseReview, MerchandiseReviewDto, MerchandiseReviewSimpleDto> {

    public MerchandiseReviewController(MerchandiseReviewService merchandisereviewService,
                                    MerchandiseReviewMapper merchandisereviewMapper) {
        super(merchandisereviewService, merchandisereviewMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseReviewDto>> getAllMerchandiseReviews(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseReviewDto>> searchMerchandiseReviews(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseReview.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseReviewDto> getMerchandiseReviewById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseReviewDto> createMerchandiseReview(
            @Valid @RequestBody MerchandiseReviewDto merchandisereviewDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseReview entity = mapper.toEntity(merchandisereviewDto);
        MerchandiseReview saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandisereviews/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseReviewDto>> createAllMerchandiseReviews(
            @Valid @RequestBody List<MerchandiseReviewDto> merchandisereviewDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseReview> entities = mapper.toEntityList(merchandisereviewDtoList);
        List<MerchandiseReview> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisereviews").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseReviewDto> updateMerchandiseReview(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseReviewDto merchandisereviewDto) {

        MerchandiseReview entityToUpdate = mapper.toEntity(merchandisereviewDto);
        MerchandiseReview updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseReview(@PathVariable Long id) {
        return doDelete(id);
    }
}