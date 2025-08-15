package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentRatingBoardDto;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.mapper.ContentRatingBoardMapper;
import com.example.modules.entertainment_ecosystem.service.ContentRatingBoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contentratingboards")
public class ContentRatingBoardController {

    private final ContentRatingBoardService contentratingboardService;
    private final ContentRatingBoardMapper contentratingboardMapper;

    public ContentRatingBoardController(ContentRatingBoardService contentratingboardService,
                                    ContentRatingBoardMapper contentratingboardMapper) {
        this.contentratingboardService = contentratingboardService;
        this.contentratingboardMapper = contentratingboardMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContentRatingBoardDto>> getAllContentRatingBoards() {
        List<ContentRatingBoard> entities = contentratingboardService.findAll();
        return ResponseEntity.ok(contentratingboardMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentRatingBoardDto> getContentRatingBoardById(@PathVariable Long id) {
        return contentratingboardService.findById(id)
                .map(contentratingboardMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContentRatingBoardDto> createContentRatingBoard(
            @Valid @RequestBody ContentRatingBoardDto contentratingboardDto,
            UriComponentsBuilder uriBuilder) {

        ContentRatingBoard entity = contentratingboardMapper.toEntity(contentratingboardDto);
        ContentRatingBoard saved = contentratingboardService.save(entity);
        URI location = uriBuilder.path("/api/contentratingboards/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(contentratingboardMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ContentRatingBoardDto> updateContentRatingBoard(
                @PathVariable Long id,
                @RequestBody ContentRatingBoardDto contentratingboardDto) {

                // Transformer le DTO en entity pour le service
                ContentRatingBoard entityToUpdate = contentratingboardMapper.toEntity(contentratingboardDto);

                // Appel du service update
                ContentRatingBoard updatedEntity = contentratingboardService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ContentRatingBoardDto updatedDto = contentratingboardMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteContentRatingBoard(@PathVariable Long id) {
                    boolean deleted = contentratingboardService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}