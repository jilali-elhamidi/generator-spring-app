package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.TimeLog;
import com.example.modules.project_management.repository.TimeLogRepository;
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
public class TimeLogService extends BaseService<TimeLog> {

    protected final TimeLogRepository timelogRepository;
    private final TaskRepository taskRepository;
    private final TeamMemberRepository userRepository;

    public TimeLogService(TimeLogRepository repository, TaskRepository taskRepository, TeamMemberRepository userRepository)
    {
        super(repository);
        this.timelogRepository = repository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TimeLog save(TimeLog timelog) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (timelog.getTask() != null &&
            timelog.getTask().getId() != null) {

            Task existingTask = taskRepository.findById(
                timelog.getTask().getId()
            ).orElseThrow(() -> new RuntimeException("Task not found"));

            timelog.setTask(existingTask);
        }
        
        if (timelog.getUser() != null &&
            timelog.getUser().getId() != null) {

            TeamMember existingUser = userRepository.findById(
                timelog.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            timelog.setUser(existingUser);
        }
        
    // ---------- OneToOne ----------
    return timelogRepository.save(timelog);
}


    public TimeLog update(Long id, TimeLog timelogRequest) {
        TimeLog existing = timelogRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TimeLog not found"));

    // Copier les champs simples
        existing.setDate(timelogRequest.getDate());
        existing.setHours(timelogRequest.getHours());
        existing.setDescription(timelogRequest.getDescription());

    // ---------- Relations ManyToOne ----------
        if (timelogRequest.getTask() != null &&
            timelogRequest.getTask().getId() != null) {

            Task existingTask = taskRepository.findById(
                timelogRequest.getTask().getId()
            ).orElseThrow(() -> new RuntimeException("Task not found"));

            existing.setTask(existingTask);
        } else {
            existing.setTask(null);
        }
        
        if (timelogRequest.getUser() != null &&
            timelogRequest.getUser().getId() != null) {

            TeamMember existingUser = userRepository.findById(
                timelogRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return timelogRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<TimeLog> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        TimeLog entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getTask() != null) {
            entity.setTask(null);
        }
        
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        repository.delete(entity);
        return true;
    }
}