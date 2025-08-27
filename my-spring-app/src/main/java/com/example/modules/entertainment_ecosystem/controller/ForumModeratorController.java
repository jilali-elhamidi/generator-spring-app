package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumModeratorDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumModeratorSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import com.example.modules.entertainment_ecosystem.mapper.ForumModeratorMapper;
import com.example.modules.entertainment_ecosystem.service.ForumModeratorService;
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
 * Controller for managing ForumModerator entities.
 */
@RestController
@RequestMapping("/api/forummoderators")
public class ForumModeratorController extends BaseController<ForumModerator, ForumModeratorDto, ForumModeratorSimpleDto> {

    public ForumModeratorController(ForumModeratorService forummoderatorService,
                                    ForumModeratorMapper forummoderatorMapper) {
        super(forummoderatorService, forummoderatorMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ForumModeratorDto>> getAllForumModerators(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ForumModeratorDto>> searchForumModerators(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ForumModerator.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumModeratorDto> getForumModeratorById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ForumModeratorDto> createForumModerator(
            @Valid @RequestBody ForumModeratorDto forummoderatorDto,
            UriComponentsBuilder uriBuilder) {

        ForumModerator entity = mapper.toEntity(forummoderatorDto);
        ForumModerator saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/forummoderators/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ForumModeratorDto>> createAllForumModerators(
            @Valid @RequestBody List<ForumModeratorDto> forummoderatorDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ForumModerator> entities = mapper.toEntityList(forummoderatorDtoList);
        List<ForumModerator> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/forummoderators").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumModeratorDto> updateForumModerator(
            @PathVariable Long id,
            @Valid @RequestBody ForumModeratorDto forummoderatorDto) {

        ForumModerator entityToUpdate = mapper.toEntity(forummoderatorDto);
        ForumModerator updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumModerator(@PathVariable Long id) {
        return doDelete(id);
    }
}