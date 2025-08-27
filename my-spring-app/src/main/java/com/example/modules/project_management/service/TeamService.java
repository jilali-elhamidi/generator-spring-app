package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Team;
import com.example.modules.project_management.repository.TeamRepository;

import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;

import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;

import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TeamService extends BaseService<Team> {

    protected final TeamRepository teamRepository;
    
    protected final TeamMemberRepository membersRepository;
    
    protected final TeamMemberRepository teamLeadRepository;
    
    protected final ProjectRepository teamProjectsRepository;
    

    public TeamService(TeamRepository repository, TeamMemberRepository membersRepository, TeamMemberRepository teamLeadRepository, ProjectRepository teamProjectsRepository)
    {
        super(repository);
        this.teamRepository = repository;
        
        this.membersRepository = membersRepository;
        
        this.teamLeadRepository = teamLeadRepository;
        
        this.teamProjectsRepository = teamProjectsRepository;
        
    }

    @Transactional
    @Override
    public Team save(Team team) {
    // ---------- OneToMany ----------
        if (team.getMembers() != null) {
            List<TeamMember> managedMembers = new ArrayList<>();
            for (TeamMember item : team.getMembers()) {
                if (item.getId() != null) {
                    TeamMember existingItem = membersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TeamMember not found"));

                     existingItem.setTeam(team);
                     managedMembers.add(existingItem);
                } else {
                    item.setTeam(team);
                    managedMembers.add(item);
                }
            }
            team.setMembers(managedMembers);
        }
    
        if (team.getTeamProjects() != null) {
            List<Project> managedTeamProjects = new ArrayList<>();
            for (Project item : team.getTeamProjects()) {
                if (item.getId() != null) {
                    Project existingItem = teamProjectsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Project not found"));

                     existingItem.setTeam(team);
                     managedTeamProjects.add(existingItem);
                } else {
                    item.setTeam(team);
                    managedTeamProjects.add(item);
                }
            }
            team.setTeamProjects(managedTeamProjects);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (team.getTeamLead() != null) {
            if (team.getTeamLead().getId() != null) {
                TeamMember existingTeamLead = teamLeadRepository.findById(team.getTeamLead().getId())
                    .orElseThrow(() -> new RuntimeException("TeamMember not found with id "
                        + team.getTeamLead().getId()));
                team.setTeamLead(existingTeamLead);
            } else {
                // Nouvel objet → sauvegarde d'abord
                TeamMember newTeamLead = teamLeadRepository.save(team.getTeamLead());
                team.setTeamLead(newTeamLead);
            }

            team.getTeamLead().setManagedTeam(team);
        }
        
    return teamRepository.save(team);
}

    @Transactional
    @Override
    public Team update(Long id, Team teamRequest) {
        Team existing = teamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team not found"));

    // Copier les champs simples
        existing.setName(teamRequest.getName());
        existing.setMotto(teamRequest.getMotto());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getMembers().clear();

        if (teamRequest.getMembers() != null) {
            for (var item : teamRequest.getMembers()) {
                TeamMember existingItem;
                if (item.getId() != null) {
                    existingItem = membersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TeamMember not found"));
                } else {
                existingItem = item;
                }

                existingItem.setTeam(existing);
                existing.getMembers().add(existingItem);
            }
        }
        
        existing.getTeamProjects().clear();

        if (teamRequest.getTeamProjects() != null) {
            for (var item : teamRequest.getTeamProjects()) {
                Project existingItem;
                if (item.getId() != null) {
                    existingItem = teamProjectsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Project not found"));
                } else {
                existingItem = item;
                }

                existingItem.setTeam(existing);
                existing.getTeamProjects().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (teamRequest.getTeamLead() != null &&teamRequest.getTeamLead().getId() != null) {

        TeamMember teamLead = teamLeadRepository.findById(teamRequest.getTeamLead().getId())
                .orElseThrow(() -> new RuntimeException("TeamMember not found"));

        existing.setTeamLead(teamLead);
        teamLead.setManagedTeam(existing);
        }
    
    return teamRepository.save(existing);
}

    // Pagination simple
    public Page<Team> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Team> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Team.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Team> saveAll(List<Team> teamList) {
        return super.saveAll(teamList);
    }

}