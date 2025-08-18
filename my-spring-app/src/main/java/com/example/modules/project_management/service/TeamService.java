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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TeamService extends BaseService<Team> {

    protected final TeamRepository teamRepository;
    private final TeamMemberRepository membersRepository;
    private final TeamMemberRepository teamLeadRepository;
    private final ProjectRepository teamProjectsRepository;

    public TeamService(TeamRepository repository,TeamMemberRepository membersRepository,TeamMemberRepository teamLeadRepository,ProjectRepository teamProjectsRepository)
    {
        super(repository);
        this.teamRepository = repository;
        this.membersRepository = membersRepository;
        this.teamLeadRepository = teamLeadRepository;
        this.teamProjectsRepository = teamProjectsRepository;
    }

    @Override
    public Team save(Team team) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (team.getMembers() != null) {
            List<TeamMember> managedMembers = new ArrayList<>();
            for (TeamMember item : team.getMembers()) {
            if (item.getId() != null) {
            TeamMember existingItem = membersRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("TeamMember not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTeam(team);
            managedMembers.add(existingItem);
            } else {
            item.setTeam(team);
            managedMembers.add(item);
            }
            }
            team.setMembers(managedMembers);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (team.getTeamProjects() != null) {
            List<Project> managedTeamProjects = new ArrayList<>();
            for (Project item : team.getTeamProjects()) {
            if (item.getId() != null) {
            Project existingItem = teamProjectsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Project not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTeam(team);
            managedTeamProjects.add(existingItem);
            } else {
            item.setTeam(team);
            managedTeamProjects.add(item);
            }
            }
            team.setTeamProjects(managedTeamProjects);
            }
        
    

    
    
    
        if (team.getTeamLead() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            team.setTeamLead(
            teamLeadRepository.findById(team.getTeamLead().getId())
            .orElseThrow(() -> new RuntimeException("teamLead not found"))
            );
        
        team.getTeamLead().setManagedTeam(team);
        }

        return teamRepository.save(team);
    }


    public Team update(Long id, Team teamRequest) {
        Team existing = teamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team not found"));

    // Copier les champs simples
        existing.setName(teamRequest.getName());
        existing.setMotto(teamRequest.getMotto());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getMembers().clear();

        if (teamRequest.getMembers() != null) {
        for (var item : teamRequest.getMembers()) {
        TeamMember existingItem;
        if (item.getId() != null) {
        existingItem = membersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TeamMember not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setTeam(existing);

        // Ajouter directement dans la collection existante
        existing.getMembers().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getTeamProjects().clear();

        if (teamRequest.getTeamProjects() != null) {
        for (var item : teamRequest.getTeamProjects()) {
        Project existingItem;
        if (item.getId() != null) {
        existingItem = teamProjectsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Project not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setTeam(existing);

        // Ajouter directement dans la collection existante
        existing.getTeamProjects().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

        if (teamRequest.getTeamLead() != null
        && teamRequest.getTeamLead().getId() != null) {

        TeamMember teamLead = teamLeadRepository.findById(
        teamRequest.getTeamLead().getId()
        ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setTeamLead(teamLead);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            teamLead.setManagedTeam(existing);
        
        }

    

    


        return teamRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Team> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Team entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getMembers() != null) {
        for (var child : entity.getMembers()) {
        
            child.setTeam(null); // retirer la référence inverse
        
        }
        entity.getMembers().clear();
        }
    

    

    
        if (entity.getTeamProjects() != null) {
        for (var child : entity.getTeamProjects()) {
        
            child.setTeam(null); // retirer la référence inverse
        
        }
        entity.getTeamProjects().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    

    


// --- Dissocier OneToOne ---

    

    
        if (entity.getTeamLead() != null) {
        // Dissocier côté inverse automatiquement
        entity.getTeamLead().setManagedTeam(null);
        // Dissocier côté direct
        entity.setTeamLead(null);
        }
    

    


// --- Dissocier ManyToOne ---

    

    

    


repository.delete(entity);
return true;
}
}