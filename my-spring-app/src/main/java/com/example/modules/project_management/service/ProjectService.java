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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProjectService extends BaseService<Project> {

    protected final ProjectRepository projectRepository;
    private final TaskRepository tasksRepository;
    private final TeamMemberRepository teamMembersRepository;
    private final TeamMemberRepository projectManagerRepository;
    private final DocumentRepository documentsRepository;
    private final MilestoneRepository milestonesRepository;
    private final ClientRepository clientRepository;
    private final TeamRepository teamRepository;

    public ProjectService(ProjectRepository repository,TaskRepository tasksRepository,TeamMemberRepository teamMembersRepository,TeamMemberRepository projectManagerRepository,DocumentRepository documentsRepository,MilestoneRepository milestonesRepository,ClientRepository clientRepository,TeamRepository teamRepository)
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

    @Override
    public Project save(Project project) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (project.getTasks() != null) {
            List<Task> managedTasks = new ArrayList<>();
            for (Task item : project.getTasks()) {
            if (item.getId() != null) {
            Task existingItem = tasksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Task not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProject(project);
            managedTasks.add(existingItem);
            } else {
            item.setProject(project);
            managedTasks.add(item);
            }
            }
            project.setTasks(managedTasks);
            }
        
    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (project.getDocuments() != null) {
            List<Document> managedDocuments = new ArrayList<>();
            for (Document item : project.getDocuments()) {
            if (item.getId() != null) {
            Document existingItem = documentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Document not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProject(project);
            managedDocuments.add(existingItem);
            } else {
            item.setProject(project);
            managedDocuments.add(item);
            }
            }
            project.setDocuments(managedDocuments);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (project.getMilestones() != null) {
            List<Milestone> managedMilestones = new ArrayList<>();
            for (Milestone item : project.getMilestones()) {
            if (item.getId() != null) {
            Milestone existingItem = milestonesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Milestone not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProject(project);
            managedMilestones.add(existingItem);
            } else {
            item.setProject(project);
            managedMilestones.add(item);
            }
            }
            project.setMilestones(managedMilestones);
            }
        
    

    

    

    
    
    if (project.getProjectManager() != null
        && project.getProjectManager().getId() != null) {
        TeamMember existingProjectManager = projectManagerRepository.findById(
        project.getProjectManager().getId()
        ).orElseThrow(() -> new RuntimeException("TeamMember not found"));
        project.setProjectManager(existingProjectManager);
        }
    
    
    
    if (project.getClient() != null
        && project.getClient().getId() != null) {
        Client existingClient = clientRepository.findById(
        project.getClient().getId()
        ).orElseThrow(() -> new RuntimeException("Client not found"));
        project.setClient(existingClient);
        }
    
    if (project.getTeam() != null
        && project.getTeam().getId() != null) {
        Team existingTeam = teamRepository.findById(
        project.getTeam().getId()
        ).orElseThrow(() -> new RuntimeException("Team not found"));
        project.setTeam(existingTeam);
        }
    

        return projectRepository.save(project);
    }


    public Project update(Long id, Project projectRequest) {
        Project existing = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));

    // Copier les champs simples
        existing.setProjectName(projectRequest.getProjectName());
        existing.setDescription(projectRequest.getDescription());
        existing.setStartDate(projectRequest.getStartDate());
        existing.setDeadline(projectRequest.getDeadline());
        existing.setStatus(projectRequest.getStatus());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

        if (projectRequest.getTeamMembers() != null) {
            existing.getTeamMembers().clear();
            List<TeamMember> teamMembersList = projectRequest.getTeamMembers().stream()
                .map(item -> teamMembersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TeamMember not found")))
                .collect(Collectors.toList());
        existing.getTeamMembers().addAll(teamMembersList);
        }

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getTasks().clear();

        if (projectRequest.getTasks() != null) {
        for (var item : projectRequest.getTasks()) {
        Task existingItem;
        if (item.getId() != null) {
        existingItem = tasksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Task not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProject(existing);

        // Ajouter directement dans la collection existante
        existing.getTasks().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getDocuments().clear();

        if (projectRequest.getDocuments() != null) {
        for (var item : projectRequest.getDocuments()) {
        Document existingItem;
        if (item.getId() != null) {
        existingItem = documentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Document not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProject(existing);

        // Ajouter directement dans la collection existante
        existing.getDocuments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getMilestones().clear();

        if (projectRequest.getMilestones() != null) {
        for (var item : projectRequest.getMilestones()) {
        Milestone existingItem;
        if (item.getId() != null) {
        existingItem = milestonesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Milestone not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProject(existing);

        // Ajouter directement dans la collection existante
        existing.getMilestones().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

    


        return projectRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Project> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Project entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getTasks() != null) {
        for (var child : entity.getTasks()) {
        
            child.setProject(null); // retirer la référence inverse
        
        }
        entity.getTasks().clear();
        }
    

    

    

    
        if (entity.getDocuments() != null) {
        for (var child : entity.getDocuments()) {
        
            child.setProject(null); // retirer la référence inverse
        
        }
        entity.getDocuments().clear();
        }
    

    
        if (entity.getMilestones() != null) {
        for (var child : entity.getMilestones()) {
        
            child.setProject(null); // retirer la référence inverse
        
        }
        entity.getMilestones().clear();
        }
    

    

    


// --- Dissocier ManyToMany ---

    

    
        if (entity.getTeamMembers() != null) {
        entity.getTeamMembers().clear();
        }
    

    

    

    

    

    


// --- Dissocier OneToOne ---

    

    

    

    

    

    

    


// --- Dissocier ManyToOne ---

    

    

    
        if (entity.getProjectManager() != null) {
        entity.setProjectManager(null);
        }
    

    

    

    
        if (entity.getClient() != null) {
        entity.setClient(null);
        }
    

    
        if (entity.getTeam() != null) {
        entity.setTeam(null);
        }
    


repository.delete(entity);
return true;
}
}