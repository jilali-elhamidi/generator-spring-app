package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.BookCharacterDto;
import com.example.modules.entertainment_ecosystem.dtosimple.BookCharacterSimpleDto;
import com.example.modules.entertainment_ecosystem.model.BookCharacter;
import com.example.modules.entertainment_ecosystem.mapper.BookCharacterMapper;
import com.example.modules.entertainment_ecosystem.service.BookCharacterService;
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
 * Controller for managing BookCharacter entities.
 */
@RestController
@RequestMapping("/api/bookcharacters")
public class BookCharacterController extends BaseController<BookCharacter, BookCharacterDto, BookCharacterSimpleDto> {

    public BookCharacterController(BookCharacterService bookcharacterService,
                                    BookCharacterMapper bookcharacterMapper) {
        super(bookcharacterService, bookcharacterMapper);
    }

    @GetMapping
    public ResponseEntity<Page<BookCharacterDto>> getAllBookCharacters(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookCharacterDto>> searchBookCharacters(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(BookCharacter.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCharacterDto> getBookCharacterById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<BookCharacterDto> createBookCharacter(
            @Valid @RequestBody BookCharacterDto bookcharacterDto,
            UriComponentsBuilder uriBuilder) {

        BookCharacter entity = mapper.toEntity(bookcharacterDto);
        BookCharacter saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/bookcharacters/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<BookCharacterDto>> createAllBookCharacters(
            @Valid @RequestBody List<BookCharacterDto> bookcharacterDtoList,
            UriComponentsBuilder uriBuilder) {

        List<BookCharacter> entities = mapper.toEntityList(bookcharacterDtoList);
        List<BookCharacter> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/bookcharacters").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCharacterDto> updateBookCharacter(
            @PathVariable Long id,
            @Valid @RequestBody BookCharacterDto bookcharacterDto) {

        BookCharacter entityToUpdate = mapper.toEntity(bookcharacterDto);
        BookCharacter updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookCharacter(@PathVariable Long id) {
        return doDelete(id);
    }
}