package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.BookDto;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.mapper.BookMapper;
import com.example.modules.entertainment_ecosystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService,
                                    BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<Book> entities = bookService.findAll();
        return ResponseEntity.ok(bookMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(bookMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(
            @Valid @RequestBody BookDto bookDto,
            UriComponentsBuilder uriBuilder) {

        Book entity = bookMapper.toEntity(bookDto);
        Book saved = bookService.save(entity);

        URI location = uriBuilder
                                .path("/api/books/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(bookMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<BookDto>> createAllBooks(
            @Valid @RequestBody List<BookDto> bookDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Book> entities = bookMapper.toEntityList(bookDtoList);
        List<Book> savedEntities = bookService.saveAll(entities);

        URI location = uriBuilder.path("/api/books").build().toUri();

        return ResponseEntity.created(location).body(bookMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDto bookDto) {


        Book entityToUpdate = bookMapper.toEntity(bookDto);
        Book updatedEntity = bookService.update(id, entityToUpdate);

        return ResponseEntity.ok(bookMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}