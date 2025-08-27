package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.BookSeriesDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookSeriesSimpleDto;
import com.example.modules.entertainment_ecosystem.model.BookSeries;
import com.example.modules.entertainment_ecosystem.mapper.BookSeriesMapper;
import com.example.modules.entertainment_ecosystem.service.BookSeriesService;
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
 * Controller for managing BookSeries entities.
 */
@RestController
@RequestMapping("/api/bookseriess")
public class BookSeriesController extends BaseController<BookSeries, BookSeriesDto, BookSeriesSimpleDto> {

    public BookSeriesController(BookSeriesService bookseriesService,
                                    BookSeriesMapper bookseriesMapper) {
        super(bookseriesService, bookseriesMapper);
    }

    @GetMapping
    public ResponseEntity<Page<BookSeriesDto>> getAllBookSeriess(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookSeriesDto>> searchBookSeriess(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(BookSeries.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookSeriesDto> getBookSeriesById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<BookSeriesDto> createBookSeries(
            @Valid @RequestBody BookSeriesDto bookseriesDto,
            UriComponentsBuilder uriBuilder) {

        BookSeries entity = mapper.toEntity(bookseriesDto);
        BookSeries saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/bookseriess/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<BookSeriesDto>> createAllBookSeriess(
            @Valid @RequestBody List<BookSeriesDto> bookseriesDtoList,
            UriComponentsBuilder uriBuilder) {

        List<BookSeries> entities = mapper.toEntityList(bookseriesDtoList);
        List<BookSeries> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/bookseriess").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookSeriesDto> updateBookSeries(
            @PathVariable Long id,
            @Valid @RequestBody BookSeriesDto bookseriesDto) {

        BookSeries entityToUpdate = mapper.toEntity(bookseriesDto);
        BookSeries updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookSeries(@PathVariable Long id) {
        return doDelete(id);
    }
}