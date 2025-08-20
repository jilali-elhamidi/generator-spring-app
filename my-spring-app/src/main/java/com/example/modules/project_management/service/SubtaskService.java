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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class SubtaskService extends BaseService<Subtask> {

    protected final SubtaskRepository subtaskRepository;
    private final TaskRepository parentTaskRepository;
    private final TeamMemberRepository assigneeRepository;

    public SubtaskService(SubtaskRepository repository, TaskRepository parentTaskRepository, TeamMemberRepository assigneeRepository)
    {
        super(repository);
        this.subtaskRepository = repository;
        this.parentTaskRepository = parentTaskRepository;
        this.assigneeRepository = assigneeRepository;
    }

    @Override
    public Subtask save(Subtask subtask) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (subtask.getParentTask() != null &&
            subtask.getParentTask().getId() != null) {

            Task existingParentTask = parentTaskRepository.findById(
                subtask.getParentTask().getId()
            ).orElseThrow(() -> new RuntimeException("Task not found"));

            subtask.setParentTask(existingParentTask);
        }
        
        if (subtask.getAssignee() != null &&
            subtask.getAssignee().getId() != null) {

            TeamMember existingAssignee = assigneeRepository.findById(
                subtask.getAssignee().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            subtask.setAssignee(existingAssignee);
        }
        
    // ---------- OneToOne ----------
    return subtaskRepository.save(subtask);
}


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
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return subtaskRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Subtask> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Subtask entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getParentTask() != null) {
            entity.setParentTask(null);
        }
        
        if (entity.getAssignee() != null) {
            entity.setAssignee(null);
        }
        
        repository.delete(entity);
        return true;
    }
}