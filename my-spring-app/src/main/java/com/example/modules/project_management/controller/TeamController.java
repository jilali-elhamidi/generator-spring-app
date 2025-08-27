package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TeamDto;
import com.example.modules.project_management.dtosimple.TeamSimpleDto;
import com.example.modules.project_management.model.Team;
import com.example.modules.project_management.mapper.TeamMapper;
import com.example.modules.project_management.service.TeamService;
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
 * Controller for managing Team entities.
 */
@RestController
@RequestMapping("/api/teams")
public class TeamController extends BaseController<Team, TeamDto, TeamSimpleDto> {

    public TeamController(TeamService teamService,
                                    TeamMapper teamMapper) {
        super(teamService, teamMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TeamDto>> getAllTeams(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TeamDto>> searchTeams(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Team.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(
            @Valid @RequestBody TeamDto teamDto,
            UriComponentsBuilder uriBuilder) {

        Team entity = mapper.toEntity(teamDto);
        Team saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/teams/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TeamDto>> createAllTeams(
            @Valid @RequestBody List<TeamDto> teamDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Team> entities = mapper.toEntityList(teamDtoList);
        List<Team> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/teams").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody TeamDto teamDto) {

        Team entityToUpdate = mapper.toEntity(teamDto);
        Team updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        return doDelete(id);
    }
}