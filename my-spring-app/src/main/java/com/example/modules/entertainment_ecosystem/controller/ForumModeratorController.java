package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumModeratorDto;
import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import com.example.modules.entertainment_ecosystem.mapper.ForumModeratorMapper;
import com.example.modules.entertainment_ecosystem.service.ForumModeratorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/forummoderators")
public class ForumModeratorController {

    private final ForumModeratorService forummoderatorService;
    private final ForumModeratorMapper forummoderatorMapper;

    public ForumModeratorController(ForumModeratorService forummoderatorService,
                                    ForumModeratorMapper forummoderatorMapper) {
        this.forummoderatorService = forummoderatorService;
        this.forummoderatorMapper = forummoderatorMapper;
    }

    @GetMapping
    public ResponseEntity<List<ForumModeratorDto>> getAllForumModerators() {
        List<ForumModerator> entities = forummoderatorService.findAll();
        return ResponseEntity.ok(forummoderatorMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumModeratorDto> getForumModeratorById(@PathVariable Long id) {
        return forummoderatorService.findById(id)
                .map(forummoderatorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ForumModeratorDto> createForumModerator(
            @Valid @RequestBody ForumModeratorDto forummoderatorDto,
            UriComponentsBuilder uriBuilder) {

        ForumModerator entity = forummoderatorMapper.toEntity(forummoderatorDto);
        ForumModerator saved = forummoderatorService.save(entity);

        URI location = uriBuilder
                                .path("/api/forummoderators/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(forummoderatorMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ForumModeratorDto>> createAllForumModerators(
            @Valid @RequestBody List<ForumModeratorDto> forummoderatorDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ForumModerator> entities = forummoderatorMapper.toEntityList(forummoderatorDtoList);
        List<ForumModerator> savedEntities = forummoderatorService.saveAll(entities);

        URI location = uriBuilder.path("/api/forummoderators").build().toUri();

        return ResponseEntity.created(location).body(forummoderatorMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumModeratorDto> updateForumModerator(
            @PathVariable Long id,
            @Valid @RequestBody ForumModeratorDto forummoderatorDto) {


        ForumModerator entityToUpdate = forummoderatorMapper.toEntity(forummoderatorDto);
        ForumModerator updatedEntity = forummoderatorService.update(id, entityToUpdate);

        return ResponseEntity.ok(forummoderatorMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumModerator(@PathVariable Long id) {
        boolean deleted = forummoderatorService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}