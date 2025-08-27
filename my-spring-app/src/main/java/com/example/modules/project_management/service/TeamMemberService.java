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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TeamMemberService extends BaseService<TeamMember> {

    protected final TeamMemberRepository teammemberRepository;
    
    protected final TaskRepository assignedTasksRepository;
    
    protected final ProjectRepository projectsRepository;
    
    protected final ProjectRepository managedProjectsRepository;
    
    protected final CommentRepository createdCommentsRepository;
    
    protected final TeamRepository teamRepository;
    
    protected final TeamRepository managedTeamRepository;
    

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

    @Transactional
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

            List<Project> attachedProjects = new ArrayList<>();
            for (Project item : teammember.getProjects()) {
                if (item.getId() != null) {
                    Project existingItem = projectsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Project not found with id " + item.getId()));
                    attachedProjects.add(existingItem);
                } else {

                    Project newItem = projectsRepository.save(item);
                    attachedProjects.add(newItem);
                }
            }

            teammember.setProjects(attachedProjects);

            // côté propriétaire (Project → TeamMember)
            attachedProjects.forEach(it -> it.getTeamMembers().add(teammember));
        }
        
    // ---------- ManyToOne ----------
        if (teammember.getTeam() != null) {
            if (teammember.getTeam().getId() != null) {
                Team existingTeam = teamRepository.findById(
                    teammember.getTeam().getId()
                ).orElseThrow(() -> new RuntimeException("Team not found with id "
                    + teammember.getTeam().getId()));
                teammember.setTeam(existingTeam);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Team newTeam = teamRepository.save(teammember.getTeam());
                teammember.setTeam(newTeam);
            }
        }
        
    // ---------- OneToOne ----------
        if (teammember.getManagedTeam() != null) {
            if (teammember.getManagedTeam().getId() != null) {
                Team existingManagedTeam = managedTeamRepository.findById(teammember.getManagedTeam().getId())
                    .orElseThrow(() -> new RuntimeException("Team not found with id "
                        + teammember.getManagedTeam().getId()));
                teammember.setManagedTeam(existingManagedTeam);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Team newManagedTeam = managedTeamRepository.save(teammember.getManagedTeam());
                teammember.setManagedTeam(newManagedTeam);
            }

            teammember.getManagedTeam().setTeamLead(teammember);
        }
        
    return teammemberRepository.save(teammember);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<TeamMember> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<TeamMember> search(Map<String, String> filters, Pageable pageable) {
        return super.search(TeamMember.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<TeamMember> saveAll(List<TeamMember> teammemberList) {
        return super.saveAll(teammemberList);
    }

}