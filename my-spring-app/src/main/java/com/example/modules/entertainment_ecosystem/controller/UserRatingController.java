package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.UserRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserRatingSimpleDto;
import com.example.modules.entertainment_ecosystem.model.UserRating;
import com.example.modules.entertainment_ecosystem.mapper.UserRatingMapper;
import com.example.modules.entertainment_ecosystem.service.UserRatingService;
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
 * Controller for managing UserRating entities.
 */
@RestController
@RequestMapping("/api/userratings")
public class UserRatingController extends BaseController<UserRating, UserRatingDto, UserRatingSimpleDto> {

    public UserRatingController(UserRatingService userratingService,
                                    UserRatingMapper userratingMapper) {
        super(userratingService, userratingMapper);
    }

    @GetMapping
    public ResponseEntity<Page<UserRatingDto>> getAllUserRatings(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserRatingDto>> searchUserRatings(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(UserRating.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRatingDto> getUserRatingById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<UserRatingDto> createUserRating(
            @Valid @RequestBody UserRatingDto userratingDto,
            UriComponentsBuilder uriBuilder) {

        UserRating entity = mapper.toEntity(userratingDto);
        UserRating saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/userratings/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserRatingDto>> createAllUserRatings(
            @Valid @RequestBody List<UserRatingDto> userratingDtoList,
            UriComponentsBuilder uriBuilder) {

        List<UserRating> entities = mapper.toEntityList(userratingDtoList);
        List<UserRating> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/userratings").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRatingDto> updateUserRating(
            @PathVariable Long id,
            @Valid @RequestBody UserRatingDto userratingDto) {

        UserRating entityToUpdate = mapper.toEntity(userratingDto);
        UserRating updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRating(@PathVariable Long id) {
        return doDelete(id);
    }
}