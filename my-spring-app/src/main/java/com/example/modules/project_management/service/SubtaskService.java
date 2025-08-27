package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Subtask;
import com.example.modules.project_management.repository.SubtaskRepository;

import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;

import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class SubtaskService extends BaseService<Subtask> {

    protected final SubtaskRepository subtaskRepository;
    
    protected final TaskRepository parentTaskRepository;
    
    protected final TeamMemberRepository assigneeRepository;
    

    public SubtaskService(SubtaskRepository repository, TaskRepository parentTaskRepository, TeamMemberRepository assigneeRepository)
    {
        super(repository);
        this.subtaskRepository = repository;
        
        this.parentTaskRepository = parentTaskRepository;
        
        this.assigneeRepository = assigneeRepository;
        
    }

    @Transactional
    @Override
    public Subtask save(Subtask subtask) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (subtask.getParentTask() != null) {
            if (subtask.getParentTask().getId() != null) {
                Task existingParentTask = parentTaskRepository.findById(
                    subtask.getParentTask().getId()
                ).orElseThrow(() -> new RuntimeException("Task not found with id "
                    + subtask.getParentTask().getId()));
                subtask.setParentTask(existingParentTask);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Task newParentTask = parentTaskRepository.save(subtask.getParentTask());
                subtask.setParentTask(newParentTask);
            }
        }
        
        if (subtask.getAssignee() != null) {
            if (subtask.getAssignee().getId() != null) {
                TeamMember existingAssignee = assigneeRepository.findById(
                    subtask.getAssignee().getId()
                ).orElseThrow(() -> new RuntimeException("TeamMember not found with id "
                    + subtask.getAssignee().getId()));
                subtask.setAssignee(existingAssignee);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TeamMember newAssignee = assigneeRepository.save(subtask.getAssignee());
                subtask.setAssignee(newAssignee);
            }
        }
        
    // ---------- OneToOne ----------
    return subtaskRepository.save(subtask);
}

    @Transactional
    @Override
    public Subtask update(Long id, Subtask subtaskRequest) {
        Subtask existing = subtaskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subtask not found"));

    // Copier les champs simples
        existing.setTitle(subtaskRequest.getTitle());
        existing.setStatus(subtaskRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (subtaskRequest.getParentTask() != null &&
            subtaskRequest.getParentTask().getId() != null) {

            Task existingParentTask = parentTaskRepository.findById(
                subtaskRequest.getParentTask().getId()
            ).orElseThrow(() -> new RuntimeException("Task not found"));

            existing.setParentTask(existingParentTask);
        } else {
            existing.setParentTask(null);
        }
        
        if (subtaskRequest.getAssignee() != null &&
            subtaskRequest.getAssignee().getId() != null) {

            TeamMember existingAssignee = assigneeRepository.findById(
                subtaskRequest.getAssignee().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            existing.setAssignee(existingAssignee);
        } else {
            existing.setAssignee(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return subtaskRepository.save(existing);
}

    // Pagination simple
    public Page<Subtask> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Subtask> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Subtask.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Subtask> saveAll(List<Subtask> subtaskList) {
        return super.saveAll(subtaskList);
    }

}