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
        URI location = uriBuilder.path("/api/books/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(bookMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<BookDto> updateBook(
                @PathVariable Long id,
                @RequestBody BookDto bookDto) {

                // Transformer le DTO en entity pour le service
                Book entityToUpdate = bookMapper.toEntity(bookDto);

                // Appel du service update
                Book updatedEntity = bookService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                BookDto updatedDto = bookMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}