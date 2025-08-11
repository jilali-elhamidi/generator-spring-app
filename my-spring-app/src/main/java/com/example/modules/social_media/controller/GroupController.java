package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.GroupDto;
import com.example.modules.social_media.model.Group;
import com.example.modules.social_media.mapper.GroupMapper;
import com.example.modules.social_media.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

private final GroupService groupService;
private final GroupMapper groupMapper;

public GroupController(GroupService groupService, GroupMapper groupMapper) {
this.groupService = groupService;
this.groupMapper = groupMapper;
}

@GetMapping
public ResponseEntity<List<GroupDto>> getAllGroups() {
    List<Group> entities = groupService.findAll();
    return ResponseEntity.ok(groupMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long id) {
        return groupService.findById(id)
        .map(groupMapper::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<GroupDto> createGroup(
            @Valid @RequestBody GroupDto groupDto,
            UriComponentsBuilder uriBuilder) {

            Group entity = groupMapper.toEntity(groupDto);
            Group saved = groupService.save(entity);
            URI location = uriBuilder.path("/api/groups/{id}")
            .buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(groupMapper.toDto(saved));
            }

            @PutMapping("/{id}")
            public ResponseEntity<GroupDto> updateGroup(
                @PathVariable Long id,
                @Valid @RequestBody GroupDto groupDto) {

                try {
                Group updatedEntity = groupService.update(id, groupMapper.toEntity(groupDto));
                return ResponseEntity.ok(groupMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }

                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
                    groupService.deleteById(id);
                    return ResponseEntity.noContent().build();
                    }
                    }