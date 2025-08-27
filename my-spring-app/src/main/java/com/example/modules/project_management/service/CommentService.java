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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CommentService extends BaseService<Comment> {

    protected final CommentRepository commentRepository;
    
    protected final TeamMemberRepository authorRepository;
    
    protected final TaskRepository taskRepository;
    

    public CommentService(CommentRepository repository, TeamMemberRepository authorRepository, TaskRepository taskRepository)
    {
        super(repository);
        this.commentRepository = repository;
        
        this.authorRepository = authorRepository;
        
        this.taskRepository = taskRepository;
        
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (comment.getAuthor() != null) {
            if (comment.getAuthor().getId() != null) {
                TeamMember existingAuthor = authorRepository.findById(
                    comment.getAuthor().getId()
                ).orElseThrow(() -> new RuntimeException("TeamMember not found with id "
                    + comment.getAuthor().getId()));
                comment.setAuthor(existingAuthor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TeamMember newAuthor = authorRepository.save(comment.getAuthor());
                comment.setAuthor(newAuthor);
            }
        }
        
        if (comment.getTask() != null) {
            if (comment.getTask().getId() != null) {
                Task existingTask = taskRepository.findById(
                    comment.getTask().getId()
                ).orElseThrow(() -> new RuntimeException("Task not found with id "
                    + comment.getTask().getId()));
                comment.setTask(existingTask);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Task newTask = taskRepository.save(comment.getTask());
                comment.setTask(newTask);
            }
        }
        
    // ---------- OneToOne ----------
    return commentRepository.save(comment);
}

    @Transactional
    @Override
    public Comment update(Long id, Comment commentRequest) {
        Comment existing = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comment not found"));

    // Copier les champs simples
        existing.setContent(commentRequest.getContent());
        existing.setCommentDate(commentRequest.getCommentDate());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return commentRepository.save(existing);
}

    // Pagination simple
    public Page<Comment> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Comment> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Comment.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Comment> saveAll(List<Comment> commentList) {
        return super.saveAll(commentList);
    }

}