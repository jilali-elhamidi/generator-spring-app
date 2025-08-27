package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.GroupDto;
import com.example.modules.social_media.dtosimple.GroupSimpleDto;
import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.mapper.GroupMapper;
import com.example.modules.social_media.service.GroupService;
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
 * Controller for managing Group entities.
 */
@RestController
@RequestMapping("/api/groups")
public class GroupController extends BaseController<Group, GroupDto, GroupSimpleDto> {

    public GroupController(GroupService groupService,
                                    GroupMapper groupMapper) {
        super(groupService, groupMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GroupDto>> getAllGroups(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GroupDto>> searchGroups(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Group.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(
            @Valid @RequestBody GroupDto groupDto,
            UriComponentsBuilder uriBuilder) {

        Group entity = mapper.toEntity(groupDto);
        Group saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/groups/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GroupDto>> createAllGroups(
            @Valid @RequestBody List<GroupDto> groupDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Group> entities = mapper.toEntityList(groupDtoList);
        List<Group> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/groups").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody GroupDto groupDto) {

        Group entityToUpdate = mapper.toEntity(groupDto);
        Group updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return doDelete(id);
    }
}