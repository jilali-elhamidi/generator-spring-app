package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Milestone;
import com.example.modules.project_management.repository.MilestoneRepository;
import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;
import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MilestoneService extends BaseService<Milestone> {

    protected final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository relatedTasksRepository;

    public MilestoneService(MilestoneRepository repository, ProjectRepository projectRepository, TaskRepository relatedTasksRepository)
    {
        super(repository);
        this.milestoneRepository = repository;
        this.projectRepository = projectRepository;
        this.relatedTasksRepository = relatedTasksRepository;
    }

    @Override
    public Milestone save(Milestone milestone) {
    // ---------- OneToMany ----------
        if (milestone.getRelatedTasks() != null) {
            List<Task> managedRelatedTasks = new ArrayList<>();
            for (Task item : milestone.getRelatedTasks()) {
                if (item.getId() != null) {
                    Task existingItem = relatedTasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Task not found"));

                     existingItem.setMilestone(milestone);
                     managedRelatedTasks.add(existingItem);
                } else {
                    item.setMilestone(milestone);
                    managedRelatedTasks.add(item);
                }
            }
            milestone.setRelatedTasks(managedRelatedTasks);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (milestone.getProject() != null &&
            milestone.getProject().getId() != null) {

            Project existingProject = projectRepository.findById(
                milestone.getProject().getId()
            ).orElseThrow(() -> new RuntimeException("Project not found"));

            milestone.setProject(existingProject);
        }
        
    // ---------- OneToOne ----------
    return milestoneRepository.save(milestone);
}


    public Milestone update(Long id, Milestone milestoneRequest) {
        Milestone existing = milestoneRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Milestone not found"));

    // Copier les champs simples
        existing.setTitle(milestoneRequest.getTitle());
        existing.setDueDate(milestoneRequest.getDueDate());
        existing.setStatus(milestoneRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (milestoneRequest.getProject() != null &&
            milestoneRequest.getProject().getId() != null) {

            Project existingProject = projectRepository.findById(
                milestoneRequest.getProject().getId()
            ).orElseThrow(() -> new RuntimeException("Project not found"));

            existing.setProject(existingProject);
        } else {
            existing.setProject(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getRelatedTasks().clear();

        if (milestoneRequest.getRelatedTasks() != null) {
            for (var item : milestoneRequest.getRelatedTasks()) {
                Task existingItem;
                if (item.getId() != null) {
                    existingItem = relatedTasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Task not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMilestone(existing);
                existing.getRelatedTasks().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return milestoneRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Milestone> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Milestone entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getRelatedTasks() != null) {
            for (var child : entity.getRelatedTasks()) {
                // retirer la référence inverse
                child.setMilestone(null);
            }
            entity.getRelatedTasks().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getProject() != null) {
            entity.setProject(null);
        }
        
        repository.delete(entity);
        return true;
    }
}