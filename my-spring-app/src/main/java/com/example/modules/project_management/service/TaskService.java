package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;

import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.repository.ProjectRepository;

import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;

import com.example.modules.project_management.model.Subtask;
import com.example.modules.project_management.repository.SubtaskRepository;

import com.example.modules.project_management.model.Comment;
import com.example.modules.project_management.repository.CommentRepository;

import com.example.modules.project_management.model.Tag;
import com.example.modules.project_management.repository.TagRepository;

import com.example.modules.project_management.model.Attachment;
import com.example.modules.project_management.repository.AttachmentRepository;

import com.example.modules.project_management.model.TimeLog;
import com.example.modules.project_management.repository.TimeLogRepository;

import com.example.modules.project_management.model.Milestone;
import com.example.modules.project_management.repository.MilestoneRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TaskService extends BaseService<Task> {

    protected final TaskRepository taskRepository;
    
    protected final ProjectRepository projectRepository;
    
    protected final TeamMemberRepository assigneeRepository;
    
    protected final SubtaskRepository subtasksRepository;
    
    protected final CommentRepository commentsRepository;
    
    protected final TagRepository tagsRepository;
    
    protected final AttachmentRepository attachmentsRepository;
    
    protected final TimeLogRepository logRepository;
    
    protected final MilestoneRepository milestoneRepository;
    

    public TaskService(TaskRepository repository, ProjectRepository projectRepository, TeamMemberRepository assigneeRepository, SubtaskRepository subtasksRepository, CommentRepository commentsRepository, TagRepository tagsRepository, AttachmentRepository attachmentsRepository, TimeLogRepository logRepository, MilestoneRepository milestoneRepository)
    {
        super(repository);
        this.taskRepository = repository;
        
        this.projectRepository = projectRepository;
        
        this.assigneeRepository = assigneeRepository;
        
        this.subtasksRepository = subtasksRepository;
        
        this.commentsRepository = commentsRepository;
        
        this.tagsRepository = tagsRepository;
        
        this.attachmentsRepository = attachmentsRepository;
        
        this.logRepository = logRepository;
        
        this.milestoneRepository = milestoneRepository;
        
    }

    @Transactional
    @Override
    public Task save(Task task) {
    // ---------- OneToMany ----------
        if (task.getSubtasks() != null) {
            List<Subtask> managedSubtasks = new ArrayList<>();
            for (Subtask item : task.getSubtasks()) {
                if (item.getId() != null) {
                    Subtask existingItem = subtasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Subtask not found"));

                     existingItem.setParentTask(task);
                     managedSubtasks.add(existingItem);
                } else {
                    item.setParentTask(task);
                    managedSubtasks.add(item);
                }
            }
            task.setSubtasks(managedSubtasks);
        }
    
        if (task.getComments() != null) {
            List<Comment> managedComments = new ArrayList<>();
            for (Comment item : task.getComments()) {
                if (item.getId() != null) {
                    Comment existingItem = commentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));

                     existingItem.setTask(task);
                     managedComments.add(existingItem);
                } else {
                    item.setTask(task);
                    managedComments.add(item);
                }
            }
            task.setComments(managedComments);
        }
    
        if (task.getAttachments() != null) {
            List<Attachment> managedAttachments = new ArrayList<>();
            for (Attachment item : task.getAttachments()) {
                if (item.getId() != null) {
                    Attachment existingItem = attachmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Attachment not found"));

                     existingItem.setTask(task);
                     managedAttachments.add(existingItem);
                } else {
                    item.setTask(task);
                    managedAttachments.add(item);
                }
            }
            task.setAttachments(managedAttachments);
        }
    
        if (task.getLog() != null) {
            List<TimeLog> managedLog = new ArrayList<>();
            for (TimeLog item : task.getLog()) {
                if (item.getId() != null) {
                    TimeLog existingItem = logRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TimeLog not found"));

                     existingItem.setTask(task);
                     managedLog.add(existingItem);
                } else {
                    item.setTask(task);
                    managedLog.add(item);
                }
            }
            task.setLog(managedLog);
        }
    
    // ---------- ManyToMany ----------
        if (task.getTags() != null &&
            !task.getTags().isEmpty()) {

            List<Tag> attachedTags = new ArrayList<>();
            for (Tag item : task.getTags()) {
                if (item.getId() != null) {
                    Tag existingItem = tagsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Tag not found with id " + item.getId()));
                    attachedTags.add(existingItem);
                } else {

                    Tag newItem = tagsRepository.save(item);
                    attachedTags.add(newItem);
                }
            }

            task.setTags(attachedTags);

            // côté propriétaire (Tag → Task)
            attachedTags.forEach(it -> it.getTasks().add(task));
        }
        
    // ---------- ManyToOne ----------
        if (task.getProject() != null) {
            if (task.getProject().getId() != null) {
                Project existingProject = projectRepository.findById(
                    task.getProject().getId()
                ).orElseThrow(() -> new RuntimeException("Project not found with id "
                    + task.getProject().getId()));
                task.setProject(existingProject);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Project newProject = projectRepository.save(task.getProject());
                task.setProject(newProject);
            }
        }
        
        if (task.getAssignee() != null) {
            if (task.getAssignee().getId() != null) {
                TeamMember existingAssignee = assigneeRepository.findById(
                    task.getAssignee().getId()
                ).orElseThrow(() -> new RuntimeException("TeamMember not found with id "
                    + task.getAssignee().getId()));
                task.setAssignee(existingAssignee);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TeamMember newAssignee = assigneeRepository.save(task.getAssignee());
                task.setAssignee(newAssignee);
            }
        }
        
        if (task.getMilestone() != null) {
            if (task.getMilestone().getId() != null) {
                Milestone existingMilestone = milestoneRepository.findById(
                    task.getMilestone().getId()
                ).orElseThrow(() -> new RuntimeException("Milestone not found with id "
                    + task.getMilestone().getId()));
                task.setMilestone(existingMilestone);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Milestone newMilestone = milestoneRepository.save(task.getMilestone());
                task.setMilestone(newMilestone);
            }
        }
        
    // ---------- OneToOne ----------
    return taskRepository.save(task);
}

    @Transactional
    @Override
    public Task update(Long id, Task taskRequest) {
        Task existing = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    // Copier les champs simples
        existing.setTitle(taskRequest.getTitle());
        existing.setDescription(taskRequest.getDescription());
        existing.setDueDate(taskRequest.getDueDate());
        existing.setPriority(taskRequest.getPriority());
        existing.setStatus(taskRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (taskRequest.getProject() != null &&
            taskRequest.getProject().getId() != null) {

            Project existingProject = projectRepository.findById(
                taskRequest.getProject().getId()
            ).orElseThrow(() -> new RuntimeException("Project not found"));

            existing.setProject(existingProject);
        } else {
            existing.setProject(null);
        }
        
        if (taskRequest.getAssignee() != null &&
            taskRequest.getAssignee().getId() != null) {

            TeamMember existingAssignee = assigneeRepository.findById(
                taskRequest.getAssignee().getId()
            ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

            existing.setAssignee(existingAssignee);
        } else {
            existing.setAssignee(null);
        }
        
        if (taskRequest.getMilestone() != null &&
            taskRequest.getMilestone().getId() != null) {

            Milestone existingMilestone = milestoneRepository.findById(
                taskRequest.getMilestone().getId()
            ).orElseThrow(() -> new RuntimeException("Milestone not found"));

            existing.setMilestone(existingMilestone);
        } else {
            existing.setMilestone(null);
        }
        
    // ---------- Relations ManyToMany ----------
        if (taskRequest.getTags() != null) {
            existing.getTags().clear();

            List<Tag> tagsList = taskRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Tag not found")))
                .collect(Collectors.toList());

            existing.getTags().addAll(tagsList);

            // Mettre à jour le côté inverse
            tagsList.forEach(it -> {
                if (!it.getTasks().contains(existing)) {
                    it.getTasks().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getSubtasks().clear();

        if (taskRequest.getSubtasks() != null) {
            for (var item : taskRequest.getSubtasks()) {
                Subtask existingItem;
                if (item.getId() != null) {
                    existingItem = subtasksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Subtask not found"));
                } else {
                existingItem = item;
                }

                existingItem.setParentTask(existing);
                existing.getSubtasks().add(existingItem);
            }
        }
        
        existing.getComments().clear();

        if (taskRequest.getComments() != null) {
            for (var item : taskRequest.getComments()) {
                Comment existingItem;
                if (item.getId() != null) {
                    existingItem = commentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setTask(existing);
                existing.getComments().add(existingItem);
            }
        }
        
        existing.getAttachments().clear();

        if (taskRequest.getAttachments() != null) {
            for (var item : taskRequest.getAttachments()) {
                Attachment existingItem;
                if (item.getId() != null) {
                    existingItem = attachmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Attachment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setTask(existing);
                existing.getAttachments().add(existingItem);
            }
        }
        
        existing.getLog().clear();

        if (taskRequest.getLog() != null) {
            for (var item : taskRequest.getLog()) {
                TimeLog existingItem;
                if (item.getId() != null) {
                    existingItem = logRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TimeLog not found"));
                } else {
                existingItem = item;
                }

                existingItem.setTask(existing);
                existing.getLog().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return taskRepository.save(existing);
}

    // Pagination simple
    public Page<Task> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Task> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Task.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Task> saveAll(List<Task> taskList) {
        return super.saveAll(taskList);
    }

}