package com.example.modules.project_management.service;

import com.example.core.service.BaseService;
import com.example.modules.project_management.model.Comment;
import com.example.modules.project_management.repository.CommentRepository;
import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.repository.TeamMemberRepository;
import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.repository.TaskRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class CommentService extends BaseService<Comment> {

    protected final CommentRepository commentRepository;
    private final TeamMemberRepository authorRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository repository,TeamMemberRepository authorRepository,TaskRepository taskRepository)
    {
        super(repository);
        this.commentRepository = repository;
        this.authorRepository = authorRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Comment save(Comment comment) {


    

    

    if (comment.getAuthor() != null
        && comment.getAuthor().getId() != null) {
        TeamMember existingAuthor = authorRepository.findById(
        comment.getAuthor().getId()
        ).orElseThrow(() -> new RuntimeException("TeamMember not found"));
        comment.setAuthor(existingAuthor);
        }
    
    if (comment.getTask() != null
        && comment.getTask().getId() != null) {
        Task existingTask = taskRepository.findById(
        comment.getTask().getId()
        ).orElseThrow(() -> new RuntimeException("Task not found"));
        comment.setTask(existingTask);
        }
    

        return commentRepository.save(comment);
    }


    public Comment update(Long id, Comment commentRequest) {
        Comment existing = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comment not found"));

    // Copier les champs simples
        existing.setContent(commentRequest.getContent());
        existing.setCommentDate(commentRequest.getCommentDate());

// Relations ManyToOne : mise à jour conditionnelle
        if (commentRequest.getAuthor() != null &&
        commentRequest.getAuthor().getId() != null) {

        TeamMember existingAuthor = authorRepository.findById(
        commentRequest.getAuthor().getId()
        ).orElseThrow(() -> new RuntimeException("TeamMember not found"));

        existing.setAuthor(existingAuthor);
        } else {
        existing.setAuthor(null);
        }
        if (commentRequest.getTask() != null &&
        commentRequest.getTask().getId() != null) {

        Task existingTask = taskRepository.findById(
        commentRequest.getTask().getId()
        ).orElseThrow(() -> new RuntimeException("Task not found"));

        existing.setTask(existingTask);
        } else {
        existing.setTask(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return commentRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Comment> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Comment entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    


// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getAuthor() != null) {
        entity.setAuthor(null);
        }
    

    
        if (entity.getTask() != null) {
        entity.setTask(null);
        }
    


repository.delete(entity);
return true;
}
}