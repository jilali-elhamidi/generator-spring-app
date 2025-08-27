package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.BookDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.mapper.BookMapper;
import com.example.modules.entertainment_ecosystem.service.BookService;
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
 * Controller for managing Book entities.
 */
@RestController
@RequestMapping("/api/books")
public class BookController extends BaseController<Book, BookDto, BookSimpleDto> {

    public BookController(BookService bookService,
                                    BookMapper bookMapper) {
        super(bookService, bookMapper);
    }

    @GetMapping
    public ResponseEntity<Page<BookDto>> getAllBooks(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDto>> searchBooks(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Book.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(
            @Valid @RequestBody BookDto bookDto,
            UriComponentsBuilder uriBuilder) {

        Book entity = mapper.toEntity(bookDto);
        Book saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/books/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<BookDto>> createAllBooks(
            @Valid @RequestBody List<BookDto> bookDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Book> entities = mapper.toEntityList(bookDtoList);
        List<Book> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/books").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDto bookDto) {

        Book entityToUpdate = mapper.toEntity(bookDto);
        Book updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return doDelete(id);
    }
}