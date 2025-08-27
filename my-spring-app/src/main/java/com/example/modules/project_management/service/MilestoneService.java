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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MilestoneService extends BaseService<Milestone> {

    protected final MilestoneRepository milestoneRepository;
    
    protected final ProjectRepository projectRepository;
    
    protected final TaskRepository relatedTasksRepository;
    

    public MilestoneService(MilestoneRepository repository, ProjectRepository projectRepository, TaskRepository relatedTasksRepository)
    {
        super(repository);
        this.milestoneRepository = repository;
        
        this.projectRepository = projectRepository;
        
        this.relatedTasksRepository = relatedTasksRepository;
        
    }

    @Transactional
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
        if (milestone.getProject() != null) {
            if (milestone.getProject().getId() != null) {
                Project existingProject = projectRepository.findById(
                    milestone.getProject().getId()
                ).orElseThrow(() -> new RuntimeException("Project not found with id "
                    + milestone.getProject().getId()));
                milestone.setProject(existingProject);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Project newProject = projectRepository.save(milestone.getProject());
                milestone.setProject(newProject);
            }
        }
        
    // ---------- OneToOne ----------
    return milestoneRepository.save(milestone);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Milestone> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Milestone> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Milestone.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Milestone> saveAll(List<Milestone> milestoneList) {
        return super.saveAll(milestoneList);
    }

}