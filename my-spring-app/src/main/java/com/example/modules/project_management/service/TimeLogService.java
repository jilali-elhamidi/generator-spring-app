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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TimeLogService extends BaseService<TimeLog> {

    protected final TimeLogRepository timelogRepository;
    
    protected final TaskRepository taskRepository;
    
    protected final TeamMemberRepository userRepository;
    

    public TimeLogService(TimeLogRepository repository, TaskRepository taskRepository, TeamMemberRepository userRepository)
    {
        super(repository);
        this.timelogRepository = repository;
        
        this.taskRepository = taskRepository;
        
        this.userRepository = userRepository;
        
    }

    @Transactional
    @Override
    public TimeLog save(TimeLog timelog) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (timelog.getTask() != null) {
            if (timelog.getTask().getId() != null) {
                Task existingTask = taskRepository.findById(
                    timelog.getTask().getId()
                ).orElseThrow(() -> new RuntimeException("Task not found with id "
                    + timelog.getTask().getId()));
                timelog.setTask(existingTask);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Task newTask = taskRepository.save(timelog.getTask());
                timelog.setTask(newTask);
            }
        }
        
        if (timelog.getUser() != null) {
            if (timelog.getUser().getId() != null) {
                TeamMember existingUser = userRepository.findById(
                    timelog.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("TeamMember not found with id "
                    + timelog.getUser().getId()));
                timelog.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TeamMember newUser = userRepository.save(timelog.getUser());
                timelog.setUser(newUser);
            }
        }
        
    // ---------- OneToOne ----------
    return timelogRepository.save(timelog);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return timelogRepository.save(existing);
}

    // Pagination simple
    public Page<TimeLog> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<TimeLog> search(Map<String, String> filters, Pageable pageable) {
        return super.search(TimeLog.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<TimeLog> saveAll(List<TimeLog> timelogList) {
        return super.saveAll(timelogList);
    }

}