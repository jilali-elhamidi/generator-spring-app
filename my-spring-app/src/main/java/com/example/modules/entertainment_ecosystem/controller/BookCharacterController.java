package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.BookCharacterDto;
import com.example.modules.entertainment_ecosystem.model.BookCharacter;
import com.example.modules.entertainment_ecosystem.mapper.BookCharacterMapper;
import com.example.modules.entertainment_ecosystem.service.BookCharacterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookcharacters")
public class BookCharacterController {

    private final BookCharacterService bookcharacterService;
    private final BookCharacterMapper bookcharacterMapper;

    public BookCharacterController(BookCharacterService bookcharacterService,
                                    BookCharacterMapper bookcharacterMapper) {
        this.bookcharacterService = bookcharacterService;
        this.bookcharacterMapper = bookcharacterMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookCharacterDto>> getAllBookCharacters() {
        List<BookCharacter> entities = bookcharacterService.findAll();
        return ResponseEntity.ok(bookcharacterMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCharacterDto> getBookCharacterById(@PathVariable Long id) {
        return bookcharacterService.findById(id)
                .map(bookcharacterMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookCharacterDto> createBookCharacter(
            @Valid @RequestBody BookCharacterDto bookcharacterDto,
            UriComponentsBuilder uriBuilder) {

        BookCharacter entity = bookcharacterMapper.toEntity(bookcharacterDto);
        BookCharacter saved = bookcharacterService.save(entity);

        URI location = uriBuilder
                                .path("/api/bookcharacters/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(bookcharacterMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCharacterDto> updateBookCharacter(
            @PathVariable Long id,
            @Valid @RequestBody BookCharacterDto bookcharacterDto) {


        BookCharacter entityToUpdate = bookcharacterMapper.toEntity(bookcharacterDto);
        BookCharacter updatedEntity = bookcharacterService.update(id, entityToUpdate);

        return ResponseEntity.ok(bookcharacterMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookCharacter(@PathVariable Long id) {
        boolean deleted = bookcharacterService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}