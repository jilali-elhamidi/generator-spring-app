package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.BookSeriesDto;
import com.example.modules.entertainment_ecosystem.model.BookSeries;
import com.example.modules.entertainment_ecosystem.mapper.BookSeriesMapper;
import com.example.modules.entertainment_ecosystem.service.BookSeriesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookseriess")
public class BookSeriesController {

    private final BookSeriesService bookseriesService;
    private final BookSeriesMapper bookseriesMapper;

    public BookSeriesController(BookSeriesService bookseriesService,
                                    BookSeriesMapper bookseriesMapper) {
        this.bookseriesService = bookseriesService;
        this.bookseriesMapper = bookseriesMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookSeriesDto>> getAllBookSeriess() {
        List<BookSeries> entities = bookseriesService.findAll();
        return ResponseEntity.ok(bookseriesMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookSeriesDto> getBookSeriesById(@PathVariable Long id) {
        return bookseriesService.findById(id)
                .map(bookseriesMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookSeriesDto> createBookSeries(
            @Valid @RequestBody BookSeriesDto bookseriesDto,
            UriComponentsBuilder uriBuilder) {

        BookSeries entity = bookseriesMapper.toEntity(bookseriesDto);
        BookSeries saved = bookseriesService.save(entity);
        URI location = uriBuilder.path("/api/bookseriess/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(bookseriesMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<BookSeriesDto> updateBookSeries(
                @PathVariable Long id,
                @RequestBody BookSeriesDto bookseriesDto) {

                // Transformer le DTO en entity pour le service
                BookSeries entityToUpdate = bookseriesMapper.toEntity(bookseriesDto);

                // Appel du service update
                BookSeries updatedEntity = bookseriesService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                BookSeriesDto updatedDto = bookseriesMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteBookSeries(@PathVariable Long id) {
                    boolean deleted = bookseriesService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}