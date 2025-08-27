package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentRatingBoardDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingBoardSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.mapper.ContentRatingBoardMapper;
import com.example.modules.entertainment_ecosystem.service.ContentRatingBoardService;
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
 * Controller for managing ContentRatingBoard entities.
 */
@RestController
@RequestMapping("/api/contentratingboards")
public class ContentRatingBoardController extends BaseController<ContentRatingBoard, ContentRatingBoardDto, ContentRatingBoardSimpleDto> {

    public ContentRatingBoardController(ContentRatingBoardService contentratingboardService,
                                    ContentRatingBoardMapper contentratingboardMapper) {
        super(contentratingboardService, contentratingboardMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ContentRatingBoardDto>> getAllContentRatingBoards(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContentRatingBoardDto>> searchContentRatingBoards(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ContentRatingBoard.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentRatingBoardDto> getContentRatingBoardById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ContentRatingBoardDto> createContentRatingBoard(
            @Valid @RequestBody ContentRatingBoardDto contentratingboardDto,
            UriComponentsBuilder uriBuilder) {

        ContentRatingBoard entity = mapper.toEntity(contentratingboardDto);
        ContentRatingBoard saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/contentratingboards/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContentRatingBoardDto>> createAllContentRatingBoards(
            @Valid @RequestBody List<ContentRatingBoardDto> contentratingboardDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ContentRatingBoard> entities = mapper.toEntityList(contentratingboardDtoList);
        List<ContentRatingBoard> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/contentratingboards").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentRatingBoardDto> updateContentRatingBoard(
            @PathVariable Long id,
            @Valid @RequestBody ContentRatingBoardDto contentratingboardDto) {

        ContentRatingBoard entityToUpdate = mapper.toEntity(contentratingboardDto);
        ContentRatingBoard updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentRatingBoard(@PathVariable Long id) {
        return doDelete(id);
    }
}