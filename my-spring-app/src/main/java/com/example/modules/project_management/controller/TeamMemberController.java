package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TeamMemberDto;
import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.mapper.TeamMemberMapper;
import com.example.modules.project_management.service.TeamMemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teammembers")
public class TeamMemberController {

    private final TeamMemberService teammemberService;
    private final TeamMemberMapper teammemberMapper;

    public TeamMemberController(TeamMemberService teammemberService,
                                    TeamMemberMapper teammemberMapper) {
        this.teammemberService = teammemberService;
        this.teammemberMapper = teammemberMapper;
    }

    @GetMapping
    public ResponseEntity<List<TeamMemberDto>> getAllTeamMembers() {
        List<TeamMember> entities = teammemberService.findAll();
        return ResponseEntity.ok(teammemberMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDto> getTeamMemberById(@PathVariable Long id) {
        return teammemberService.findById(id)
                .map(teammemberMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeamMemberDto> createTeamMember(
            @Valid @RequestBody TeamMemberDto teammemberDto,
            UriComponentsBuilder uriBuilder) {

        TeamMember entity = teammemberMapper.toEntity(teammemberDto);
        TeamMember saved = teammemberService.save(entity);

        URI location = uriBuilder
                                .path("/api/teammembers/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(teammemberMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberDto> updateTeamMember(
            @PathVariable Long id,
            @Valid @RequestBody TeamMemberDto teammemberDto) {


        TeamMember entityToUpdate = teammemberMapper.toEntity(teammemberDto);
        TeamMember updatedEntity = teammemberService.update(id, entityToUpdate);

        return ResponseEntity.ok(teammemberMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        boolean deleted = teammemberService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}