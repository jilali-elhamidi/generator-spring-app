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

    public TeamMemberService(TeamMemberRepository repository, TaskRepository assignedTasksRepository, ProjectRepository projectsRepository, ProjectRepository managedProjectsRepository, CommentRepository createdCommentsRepository, TeamRepository teamRepository, TeamRepository managedTeamRepository)
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
    // ---------- OneToMany ----------
        if (teammember.getAssignedTasks() != null) {
            List<Task> managedAssignedTasks = new ArrayList<>();
            for (Task item : teammember.getAssignedTasks()) {
                if (item.getId() != null) {
                    Task existingItem = assignedTasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Task not found"));

                     existingItem.setAssignee(teammember);
                     managedAssignedTasks.add(existingItem);
                } else {
                    item.setAssignee(teammember);
                    managedAssignedTasks.add(item);
                }
            }
            teammember.setAssignedTasks(managedAssignedTasks);
        }
    
        if (teammember.getManagedProjects() != null) {
            List<Project> managedManagedProjects = new ArrayList<>();
            for (Project item : teammember.getManagedProjects()) {
                if (item.getId() != null) {
                    Project existingItem = managedProjectsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Project not found"));

                     existingItem.setProjectManager(teammember);
                     managedManagedProjects.add(existingItem);
                } else {
                    item.setProjectManager(teammember);
                    managedManagedProjects.add(item);
                }
            }
            teammember.setManagedProjects(managedManagedProjects);
        }
    
        if (teammember.getCreatedComments() != null) {
            List<Comment> managedCreatedComments = new ArrayList<>();
            for (Comment item : teammember.getCreatedComments()) {
                if (item.getId() != null) {
                    Comment existingItem = createdCommentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));

                     existingItem.setAuthor(teammember);
                     managedCreatedComments.add(existingItem);
                } else {
                    item.setAuthor(teammember);
                    managedCreatedComments.add(item);
                }
            }
            teammember.setCreatedComments(managedCreatedComments);
        }
    
    // ---------- ManyToMany ----------
        if (teammember.getProjects() != null &&
            !teammember.getProjects().isEmpty()) {

            List<Project> attachedProjects = teammember.getProjects().stream()
            .map(item -> projectsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Project not found with id " + item.getId())))
            .toList();

            teammember.setProjects(attachedProjects);

            // côté propriétaire (Project → TeamMember)
            attachedProjects.forEach(it -> it.getTeamMembers().add(teammember));
        }
        
    // ---------- ManyToOne ----------
        if (teammember.getTeam() != null &&
            teammember.getTeam().getId() != null) {

            Team existingTeam = teamRepository.findById(
                teammember.getTeam().getId()
            ).orElseThrow(() -> new RuntimeException("Team not found"));

            teammember.setTeam(existingTeam);
        }
        
    // ---------- OneToOne ----------
        if (teammember.getManagedTeam() != null) {

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

    // ---------- Relations ManyToOne ----------
        if (teammemberRequest.getTeam() != null &&
            teammemberRequest.getTeam().getId() != null) {

            Team existingTeam = teamRepository.findById(
                teammemberRequest.getTeam().getId()
            ).orElseThrow(() -> new RuntimeException("Team not found"));

            existing.setTeam(existingTeam);
        } else {
            existing.setTeam(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (teammemberRequest.getProjects() != null) {
            existing.getProjects().clear();

            List<Project> projectsList = teammemberRequest.getProjects().stream()
                .map(item -> projectsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Project not found")))
                .collect(Collectors.toList());

            existing.getProjects().addAll(projectsList);

            // Mettre à jour le côté inverse
            projectsList.forEach(it -> {
                if (!it.getTeamMembers().contains(existing)) {
                    it.getTeamMembers().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getAssignedTasks().clear();

        if (teammemberRequest.getAssignedTasks() != null) {
            for (var item : teammemberRequest.getAssignedTasks()) {
                Task existingItem;
                if (item.getId() != null) {
                    existingItem = assignedTasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Task not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAssignee(existing);
                existing.getAssignedTasks().add(existingItem);
            }
        }
        
        existing.getManagedProjects().clear();

        if (teammemberRequest.getManagedProjects() != null) {
            for (var item : teammemberRequest.getManagedProjects()) {
                Project existingItem;
                if (item.getId() != null) {
                    existingItem = managedProjectsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Project not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProjectManager(existing);
                existing.getManagedProjects().add(existingItem);
            }
        }
        
        existing.getCreatedComments().clear();

        if (teammemberRequest.getCreatedComments() != null) {
            for (var item : teammemberRequest.getCreatedComments()) {
                Comment existingItem;
                if (item.getId() != null) {
                    existingItem = createdCommentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAuthor(existing);
                existing.getCreatedComments().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
        if (teammemberRequest.getManagedTeam() != null &&teammemberRequest.getManagedTeam().getId() != null) {

        Team managedTeam = managedTeamRepository.findById(teammemberRequest.getManagedTeam().getId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        existing.setManagedTeam(managedTeam);
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
                // retirer la référence inverse
                child.setAssignee(null);
            }
            entity.getAssignedTasks().clear();
        }
        
        if (entity.getManagedProjects() != null) {
            for (var child : entity.getManagedProjects()) {
                // retirer la référence inverse
                child.setProjectManager(null);
            }
            entity.getManagedProjects().clear();
        }
        
        if (entity.getCreatedComments() != null) {
            for (var child : entity.getCreatedComments()) {
                // retirer la référence inverse
                child.setAuthor(null);
            }
            entity.getCreatedComments().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getProjects() != null) {
            for (Project item : new ArrayList<>(entity.getProjects())) {
                
                item.getTeamMembers().remove(entity); // retire côté inverse
            }
            entity.getProjects().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
        if (entity.getManagedTeam() != null) {
            entity.getManagedTeam().setTeamLead(null);
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