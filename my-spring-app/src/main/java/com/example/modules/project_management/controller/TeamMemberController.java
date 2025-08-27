package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TeamMemberDto;
import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;
import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.mapper.TeamMemberMapper;
import com.example.modules.project_management.service.TeamMemberService;
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
 * Controller for managing TeamMember entities.
 */
@RestController
@RequestMapping("/api/teammembers")
public class TeamMemberController extends BaseController<TeamMember, TeamMemberDto, TeamMemberSimpleDto> {

    public TeamMemberController(TeamMemberService teammemberService,
                                    TeamMemberMapper teammemberMapper) {
        super(teammemberService, teammemberMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TeamMemberDto>> getAllTeamMembers(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TeamMemberDto>> searchTeamMembers(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(TeamMember.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDto> getTeamMemberById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TeamMemberDto> createTeamMember(
            @Valid @RequestBody TeamMemberDto teammemberDto,
            UriComponentsBuilder uriBuilder) {

        TeamMember entity = mapper.toEntity(teammemberDto);
        TeamMember saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/teammembers/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TeamMemberDto>> createAllTeamMembers(
            @Valid @RequestBody List<TeamMemberDto> teammemberDtoList,
            UriComponentsBuilder uriBuilder) {

        List<TeamMember> entities = mapper.toEntityList(teammemberDtoList);
        List<TeamMember> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/teammembers").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberDto> updateTeamMember(
            @PathVariable Long id,
            @Valid @RequestBody TeamMemberDto teammemberDto) {

        TeamMember entityToUpdate = mapper.toEntity(teammemberDto);
        TeamMember updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        return doDelete(id);
    }
}