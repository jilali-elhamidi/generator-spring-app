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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TaskService extends BaseService<Task> {

    protected final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TeamMemberRepository assigneeRepository;
    private final SubtaskRepository subtasksRepository;
    private final CommentRepository commentsRepository;
    private final TagRepository tagsRepository;
    private final AttachmentRepository attachmentsRepository;
    private final TimeLogRepository logRepository;
    private final MilestoneRepository milestoneRepository;

    public TaskService(TaskRepository repository,ProjectRepository projectRepository,TeamMemberRepository assigneeRepository,SubtaskRepository subtasksRepository,CommentRepository commentsRepository,TagRepository tagsRepository,AttachmentRepository attachmentsRepository,TimeLogRepository logRepository,MilestoneRepository milestoneRepository)
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

    @Override
    public Task save(Task task) {


    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (task.getSubtasks() != null) {
            List<Subtask> managedSubtasks = new ArrayList<>();
            for (Subtask item : task.getSubtasks()) {
            if (item.getId() != null) {
            Subtask existingItem = subtasksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Subtask not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setParentTask(task);
            managedSubtasks.add(existingItem);
            } else {
            item.setParentTask(task);
            managedSubtasks.add(item);
            }
            }
            task.setSubtasks(managedSubtasks);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (task.getComments() != null) {
            List<Comment> managedComments = new ArrayList<>();
            for (Comment item : task.getComments()) {
            if (item.getId() != null) {
            Comment existingItem = commentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Comment not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTask(task);
            managedComments.add(existingItem);
            } else {
            item.setTask(task);
            managedComments.add(item);
            }
            }
            task.setComments(managedComments);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (task.getAttachments() != null) {
            List<Attachment> managedAttachments = new ArrayList<>();
            for (Attachment item : task.getAttachments()) {
            if (item.getId() != null) {
            Attachment existingItem = attachmentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Attachment not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTask(task);
            managedAttachments.add(existingItem);
            } else {
            item.setTask(task);
            managedAttachments.add(item);
            }
            }
            task.setAttachments(managedAttachments);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (task.getLog() != null) {
            List<TimeLog> managedLog = new ArrayList<>();
            for (TimeLog item : task.getLog()) {
            if (item.getId() != null) {
            TimeLog existingItem = logRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("TimeLog not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTask(task);
            managedLog.add(existingItem);
            } else {
            item.setTask(task);
            managedLog.add(item);
            }
            }
            task.setLog(managedLog);
            }
        
    

    

    if (task.getProject() != null
        && task.getProject().getId() != null) {
        Project existingProject = projectRepository.findById(
        task.getProject().getId()
        ).orElseThrow(() -> new RuntimeException("Project not found"));
        task.setProject(existingProject);
        }
    
    if (task.getAssignee() != null
        && task.getAssignee().getId() != null) {
        TeamMember existingAssignee = assigneeRepository.findById(
        task.getAssignee().getId()
        ).orElseThrow(() -> new RuntimeException("TeamMember not found"));
        task.setAssignee(existingAssignee);
        }
    
    
    
    
    
    
    if (task.getMilestone() != null
        && task.getMilestone().getId() != null) {
        Milestone existingMilestone = milestoneRepository.findById(
        task.getMilestone().getId()
        ).orElseThrow(() -> new RuntimeException("Milestone not found"));
        task.setMilestone(existingMilestone);
        }
    

        return taskRepository.save(task);
    }


    public Task update(Long id, Task taskRequest) {
        Task existing = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    // Copier les champs simples
        existing.setTitle(taskRequest.getTitle());
        existing.setDescription(taskRequest.getDescription());
        existing.setDueDate(taskRequest.getDueDate());
        existing.setPriority(taskRequest.getPriority());
        existing.setStatus(taskRequest.getStatus());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

        if (taskRequest.getTags() != null) {
            existing.getTags().clear();
            List<Tag> tagsList = taskRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Tag not found")))
                .collect(Collectors.toList());
        existing.getTags().addAll(tagsList);
        }

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getSubtasks().clear();

        if (taskRequest.getSubtasks() != null) {
        for (var item : taskRequest.getSubtasks()) {
        Subtask existingItem;
        if (item.getId() != null) {
        existingItem = subtasksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Subtask not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setParentTask(existing);

        // Ajouter directement dans la collection existante
        existing.getSubtasks().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getComments().clear();

        if (taskRequest.getComments() != null) {
        for (var item : taskRequest.getComments()) {
        Comment existingItem;
        if (item.getId() != null) {
        existingItem = commentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Comment not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setTask(existing);

        // Ajouter directement dans la collection existante
        existing.getComments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getAttachments().clear();

        if (taskRequest.getAttachments() != null) {
        for (var item : taskRequest.getAttachments()) {
        Attachment existingItem;
        if (item.getId() != null) {
        existingItem = attachmentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Attachment not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setTask(existing);

        // Ajouter directement dans la collection existante
        existing.getAttachments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getLog().clear();

        if (taskRequest.getLog() != null) {
        for (var item : taskRequest.getLog()) {
        TimeLog existingItem;
        if (item.getId() != null) {
        existingItem = logRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TimeLog not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setTask(existing);

        // Ajouter directement dans la collection existante
        existing.getLog().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

    

    


        return taskRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Task> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Task entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    
        if (entity.getSubtasks() != null) {
        for (var child : entity.getSubtasks()) {
        
            child.setParentTask(null); // retirer la référence inverse
        
        }
        entity.getSubtasks().clear();
        }
    

    
        if (entity.getComments() != null) {
        for (var child : entity.getComments()) {
        
            child.setTask(null); // retirer la référence inverse
        
        }
        entity.getComments().clear();
        }
    

    

    
        if (entity.getAttachments() != null) {
        for (var child : entity.getAttachments()) {
        
            child.setTask(null); // retirer la référence inverse
        
        }
        entity.getAttachments().clear();
        }
    

    
        if (entity.getLog() != null) {
        for (var child : entity.getLog()) {
        
            child.setTask(null); // retirer la référence inverse
        
        }
        entity.getLog().clear();
        }
    

    


// --- Dissocier ManyToMany ---

    

    

    

    

    
        if (entity.getTags() != null) {
        entity.getTags().clear();
        }
    

    

    

    


// --- Dissocier OneToOne ---

    

    

    

    

    

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getProject() != null) {
        entity.setProject(null);
        }
    

    
        if (entity.getAssignee() != null) {
        entity.setAssignee(null);
        }
    

    

    

    

    

    

    
        if (entity.getMilestone() != null) {
        entity.setMilestone(null);
        }
    


repository.delete(entity);
return true;
}
}