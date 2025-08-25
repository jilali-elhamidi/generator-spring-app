package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TeamDto;
import com.example.modules.project_management.model.Team;
import com.example.modules.project_management.mapper.TeamMapper;
import com.example.modules.project_management.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    public TeamController(TeamService teamService,
                                    TeamMapper teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<Team> entities = teamService.findAll();
        return ResponseEntity.ok(teamMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        return teamService.findById(id)
                .map(teamMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(
            @Valid @RequestBody TeamDto teamDto,
            UriComponentsBuilder uriBuilder) {

        Team entity = teamMapper.toEntity(teamDto);
        Team saved = teamService.save(entity);

        URI location = uriBuilder
                                .path("/api/teams/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(teamMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TeamDto>> createAllTeams(
            @Valid @RequestBody List<TeamDto> teamDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Team> entities = teamMapper.toEntityList(teamDtoList);
        List<Team> savedEntities = teamService.saveAll(entities);

        URI location = uriBuilder.path("/api/teams").build().toUri();

        return ResponseEntity.created(location).body(teamMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody TeamDto teamDto) {


        Team entityToUpdate = teamMapper.toEntity(teamDto);
        Team updatedEntity = teamService.update(id, entityToUpdate);

        return ResponseEntity.ok(teamMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        boolean deleted = teamService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}