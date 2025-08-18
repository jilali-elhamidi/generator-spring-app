package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;
import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;
import com.example.modules.project_management.model.Comment;
import com.example.modules.project_management.repository.CommentRepository;
import com.example.modules.project_management.model.Team;
import com.example.modules.project_management.repository.TeamRepository;
import com.example.modules.project_management.model.Team;
import com.example.modules.project_management.repository.TeamRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TeamMemberService extends BaseService<TeamMember> {

    protected final TeamMemberRepository teammemberRepository;
    private final TaskRepository assignedTasksRepository;
    private final ProjectRepository projectsRepository;
    private final ProjectRepository managedProjectsRepository;
    private final CommentRepository createdCommentsRepository;
    private final TeamRepository teamRepository;
    private final TeamRepository managedTeamRepository;

    public TeamMemberService(TeamMemberRepository repository,TaskRepository assignedTasksRepository,ProjectRepository projectsRepository,ProjectRepository managedProjectsRepository,CommentRepository createdCommentsRepository,TeamRepository teamRepository,TeamRepository managedTeamRepository)
    {
        super(repository);
        this.teammemberRepository = repository;
        this.assignedTasksRepository = assignedTasksRepository;
        this.projectsRepository = projectsRepository;
        this.managedProjectsRepository = managedProjectsRepository;
        this.createdCommentsRepository = createdCommentsRepository;
        this.teamRepository = teamRepository;
        this.managedTeamRepository = managedTeamRepository;
    }

    @Override
    public TeamMember save(TeamMember teammember) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (teammember.getAssignedTasks() != null) {
            List<Task> managedAssignedTasks = new ArrayList<>();
            for (Task item : teammember.getAssignedTasks()) {
            if (item.getId() != null) {
            Task existingItem = assignedTasksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Task not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAssignee(teammember);
            managedAssignedTasks.add(existingItem);
            } else {
            item.setAssignee(teammember);
            managedAssignedTasks.add(item);
            }
            }
            teammember.setAssignedTasks(managedAssignedTasks);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (teammember.getManagedProjects() != null) {
            List<Project> managedManagedProjects = new ArrayList<>();
            for (Project item : teammember.getManagedProjects()) {
            if (item.getId() != null) {
            Project existingItem = managedProjectsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Project not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProjectManager(teammember);
            managedManagedProjects.add(existingItem);
            } else {
            item.setProjectManager(teammember);
            managedManagedProjects.add(item);
            }
            }
            teammember.setManagedProjects(managedManagedProjects);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (teammember.getCreatedComments() != null) {
            List<Comment> managedCreatedComments = new ArrayList<>();
            for (Comment item : teammember.getCreatedComments()) {
            if (item.getId() != null) {
            Comment existingItem = createdCommentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Comment not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAuthor(teammember);
            managedCreatedComments.add(existingItem);
            } else {
            item.setAuthor(teammember);
            managedCreatedComments.add(item);
            }
            }
            teammember.setCreatedComments(managedCreatedComments);
            }
        
    

    

    

    
    
    
    
    if (teammember.getTeam() != null
        && teammember.getTeam().getId() != null) {
        Team existingTeam = teamRepository.findById(
        teammember.getTeam().getId()
        ).orElseThrow(() -> new RuntimeException("Team not found"));
        teammember.setTeam(existingTeam);
        }
    
    
        if (teammember.getManagedTeam() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            teammember.setManagedTeam(
            managedTeamRepository.findById(teammember.getManagedTeam().getId())
            .orElseThrow(() -> new RuntimeException("managedTeam not found"))
            );
        
        teammember.getManagedTeam().setTeamLead(teammember);
        }

        return teammemberRepository.save(teammember);
    }


    public TeamMember update(Long id, TeamMember teammemberRequest) {
        TeamMember existing = teammemberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TeamMember not found"));

    // Copier les champs simples
        existing.setName(teammemberRequest.getName());
        existing.setEmail(teammemberRequest.getEmail());
        existing.setRole(teammemberRequest.getRole());

// Relations ManyToOne : mise à jour conditionnelle
        if (teammemberRequest.getTeam() != null &&
        teammemberRequest.getTeam().getId() != null) {

        Team existingTeam = teamRepository.findById(
        teammemberRequest.getTeam().getId()
        ).orElseThrow(() -> new RuntimeException("Team not found"));

        existing.setTeam(existingTeam);
        } else {
        existing.setTeam(null);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (teammemberRequest.getProjects() != null) {
            existing.getProjects().clear();
            List<Project> projectsList = teammemberRequest.getProjects().stream()
                .map(item -> projectsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Project not found")))
                .collect(Collectors.toList());
        existing.getProjects().addAll(projectsList);
        }

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getAssignedTasks().clear();

        if (teammemberRequest.getAssignedTasks() != null) {
        for (var item : teammemberRequest.getAssignedTasks()) {
        Task existingItem;
        if (item.getId() != null) {
        existingItem = assignedTasksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Task not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAssignee(existing);

        // Ajouter directement dans la collection existante
        existing.getAssignedTasks().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getManagedProjects().clear();

        if (teammemberRequest.getManagedProjects() != null) {
        for (var item : teammemberRequest.getManagedProjects()) {
        Project existingItem;
        if (item.getId() != null) {
        existingItem = managedProjectsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Project not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProjectManager(existing);

        // Ajouter directement dans la collection existante
        existing.getManagedProjects().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getCreatedComments().clear();

        if (teammemberRequest.getCreatedComments() != null) {
        for (var item : teammemberRequest.getCreatedComments()) {
        Comment existingItem;
        if (item.getId() != null) {
        existingItem = createdCommentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Comment not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAuthor(existing);

        // Ajouter directement dans la collection existante
        existing.getCreatedComments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

        if (teammemberRequest.getManagedTeam() != null
        && teammemberRequest.getManagedTeam().getId() != null) {

        Team managedTeam = managedTeamRepository.findById(
        teammemberRequest.getManagedTeam().getId()
        ).orElseThrow(() -> new RuntimeException("Team not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setManagedTeam(managedTeam);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            managedTeam.setTeamLead(existing);
        
        }

    


        return teammemberRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<TeamMember> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

TeamMember entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getAssignedTasks() != null) {
        for (var child : entity.getAssignedTasks()) {
        
            child.setAssignee(null); // retirer la référence inverse
        
        }
        entity.getAssignedTasks().clear();
        }
    

    

    
        if (entity.getManagedProjects() != null) {
        for (var child : entity.getManagedProjects()) {
        
            child.setProjectManager(null); // retirer la référence inverse
        
        }
        entity.getManagedProjects().clear();
        }
    

    
        if (entity.getCreatedComments() != null) {
        for (var child : entity.getCreatedComments()) {
        
            child.setAuthor(null); // retirer la référence inverse
        
        }
        entity.getCreatedComments().clear();
        }
    

    

    


// --- Dissocier ManyToMany ---

    

    
        if (entity.getProjects() != null) {
        entity.getProjects().clear();
        }
    

    

    

    

    


// --- Dissocier OneToOne ---

    

    

    

    

    

    
        if (entity.getManagedTeam() != null) {
        // Dissocier côté inverse automatiquement
        entity.getManagedTeam().setTeamLead(null);
        // Dissocier côté direct
        entity.setManagedTeam(null);
        }
    


// --- Dissocier ManyToOne ---

    

    

    

    

    
        if (entity.getTeam() != null) {
        entity.setTeam(null);
        }
    

    


repository.delete(entity);
return true;
}
}