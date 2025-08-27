package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;

import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;

import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;

import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;

import com.example.modules.project_management.model.Document;
import com.example.modules.project_management.repository.DocumentRepository;

import com.example.modules.project_management.model.Milestone;
import com.example.modules.project_management.repository.MilestoneRepository;

import com.example.modules.project_management.model.Client;
import com.example.modules.project_management.repository.ClientRepository;

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
public class ProjectService extends BaseService<Project> {

    protected final ProjectRepository projectRepository;
    
    protected final TaskRepository tasksRepository;
    
    protected final TeamMemberRepository teamMembersRepository;
    
    protected final TeamMemberRepository projectManagerRepository;
    
    protected final DocumentRepository documentsRepository;
    
    protected final MilestoneRepository milestonesRepository;
    
    protected final ClientRepository clientRepository;
    
    protected final TeamRepository teamRepository;
    

    public ProjectService(ProjectRepository repository, TaskRepository tasksRepository, TeamMemberRepository teamMembersRepository, TeamMemberRepository projectManagerRepository, DocumentRepository documentsRepository, MilestoneRepository milestonesRepository, ClientRepository clientRepository, TeamRepository teamRepository)
    {
        super(repository);
        this.projectRepository = repository;
        
        this.tasksRepository = tasksRepository;
        
        this.teamMembersRepository = teamMembersRepository;
        
        this.projectManagerRepository = projectManagerRepository;
        
        this.documentsRepository = documentsRepository;
        
        this.milestonesRepository = milestonesRepository;
        
        this.clientRepository = clientRepository;
        
        this.teamRepository = teamRepository;
        
    }

    @Transactional
    @Override
    public Project save(Project project) {
    // ---------- OneToMany ----------
        if (project.getTasks() != null) {
            List<Task> managedTasks = new ArrayList<>();
            for (Task item : project.getTasks()) {
                if (item.getId() != null) {
                    Task existingItem = tasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Task not found"));

                     existingItem.setProject(project);
                     managedTasks.add(existingItem);
                } else {
                    item.setProject(project);
                    managedTasks.add(item);
                }
            }
            project.setTasks(managedTasks);
        }
    
        if (project.getDocuments() != null) {
            List<Document> managedDocuments = new ArrayList<>();
            for (Document item : project.getDocuments()) {
                if (item.getId() != null) {
                    Document existingItem = documentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Document not found"));

                     existingItem.setProject(project);
                     managedDocuments.add(existingItem);
                } else {
                    item.setProject(project);
                    managedDocuments.add(item);
                }
            }
            project.setDocuments(managedDocuments);
        }
    
        if (project.getMilestones() != null) {
            List<Milestone> managedMilestones = new ArrayList<>();
            for (Milestone item : project.getMilestones()) {
                if (item.getId() != null) {
                    Milestone existingItem = milestonesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Milestone not found"));

                     existingItem.setProject(project);
                     managedMilestones.add(existingItem);
                } else {
                    item.setProject(project);
                    managedMilestones.add(item);
                }
            }
            project.setMilestones(managedMilestones);
        }
    
    // ---------- ManyToMany ----------
        if (project.getTeamMembers() != null &&
            !project.getTeamMembers().isEmpty()) {

            List<TeamMember> attachedTeamMembers = new ArrayList<>();
            for (TeamMember item : project.getTeamMembers()) {
                if (item.getId() != null) {
                    TeamMember existingItem = teamMembersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TeamMember not found with id " + item.getId()));
                    attachedTeamMembers.add(existingItem);
                } else {

                    TeamMember newItem = teamMembersRepository.save(item);
                    attachedTeamMembers.add(newItem);
                }
            }

            project.setTeamMembers(attachedTeamMembers);

            // côté propriétaire (TeamMember → Project)
            attachedTeamMembers.forEach(it -> it.getProjects().add(project));
        }
        
    // ---------- ManyToOne ----------
        if (project.getProjectManager() != null) {
            if (project.getProjectManager().getId() != null) {
                TeamMember existingProjectManager = projectManagerRepository.findById(
                    project.getProjectManager().getId()
                ).orElseThrow(() -> new RuntimeException("TeamMember not found with id "
                    + project.getProjectManager().getId()));
                project.setProjectManager(existingProjectManager);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TeamMember newProjectManager = projectManagerRepository.save(project.getProjectManager());
                project.setProjectManager(newProjectManager);
            }
        }
        
        if (project.getClient() != null) {
            if (project.getClient().getId() != null) {
                Client existingClient = clientRepository.findById(
                    project.getClient().getId()
                ).orElseThrow(() -> new RuntimeException("Client not found with id "
                    + project.getClient().getId()));
                project.setClient(existingClient);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Client newClient = clientRepository.save(project.getClient());
                project.setClient(newClient);
            }
        }
        
        if (project.getTeam() != null) {
            if (project.getTeam().getId() != null) {
                Team existingTeam = teamRepository.findById(
                    project.getTeam().getId()
                ).orElseThrow(() -> new RuntimeException("Team not found with id "
                    + project.getTeam().getId()));
                project.setTeam(existingTeam);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Team newTeam = teamRepository.save(project.getTeam());
                project.setTeam(newTeam);
            }
        }
        
    // ---------- OneToOne ----------
    return projectRepository.save(project);
}

    @Transactional
    @Override
    public Project update(Long id, Project projectRequest) {
        Project existing = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));

    // Copier les champs simples
        existing.setProjectName(projectRequest.getProjectName());
        existing.setDescription(projectRequest.getDescription());
        existing.setStartDate(projectRequest.getStartDate());
        existing.setDeadline(projectRequest.getDeadline());
        existing.setStatus(projectRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (projectRequest.getProjectManager() != null &&
            projectRequest.getProjectManager().getId() != null) {

            TeamMember existingProjectManager = projectManagerRepository.findById(
                projectRequest.getProjectManager().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            existing.setProjectManager(existingProjectManager);
        } else {
            existing.setProjectManager(null);
        }
        
        if (projectRequest.getClient() != null &&
            projectRequest.getClient().getId() != null) {

            Client existingClient = clientRepository.findById(
                projectRequest.getClient().getId()
            ).orElseThrow(() -> new RuntimeException("Client not found"));

            existing.setClient(existingClient);
        } else {
            existing.setClient(null);
        }
        
        if (projectRequest.getTeam() != null &&
            projectRequest.getTeam().getId() != null) {

            Team existingTeam = teamRepository.findById(
                projectRequest.getTeam().getId()
            ).orElseThrow(() -> new RuntimeException("Team not found"));

            existing.setTeam(existingTeam);
        } else {
            existing.setTeam(null);
        }
        
    // ---------- Relations ManyToMany ----------
        if (projectRequest.getTeamMembers() != null) {
            existing.getTeamMembers().clear();

            List<TeamMember> teamMembersList = projectRequest.getTeamMembers().stream()
                .map(item -> teamMembersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TeamMember not found")))
                .collect(Collectors.toList());

            existing.getTeamMembers().addAll(teamMembersList);

            // Mettre à jour le côté inverse
            teamMembersList.forEach(it -> {
                if (!it.getProjects().contains(existing)) {
                    it.getProjects().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getTasks().clear();

        if (projectRequest.getTasks() != null) {
            for (var item : projectRequest.getTasks()) {
                Task existingItem;
                if (item.getId() != null) {
                    existingItem = tasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Task not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProject(existing);
                existing.getTasks().add(existingItem);
            }
        }
        
        existing.getDocuments().clear();

        if (projectRequest.getDocuments() != null) {
            for (var item : projectRequest.getDocuments()) {
                Document existingItem;
                if (item.getId() != null) {
                    existingItem = documentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Document not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProject(existing);
                existing.getDocuments().add(existingItem);
            }
        }
        
        existing.getMilestones().clear();

        if (projectRequest.getMilestones() != null) {
            for (var item : projectRequest.getMilestones()) {
                Milestone existingItem;
                if (item.getId() != null) {
                    existingItem = milestonesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Milestone not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProject(existing);
                existing.getMilestones().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return projectRepository.save(existing);
}

    // Pagination simple
    public Page<Project> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Project> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Project.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Project> saveAll(List<Project> projectList) {
        return super.saveAll(projectList);
    }

}