package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.repository.CommentRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.repository.PostRepository;
import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.repository.CommentRepository;
import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.repository.CommentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class CommentService extends BaseService<Comment> {

    protected final CommentRepository commentRepository;
    private final ProfileRepository authorRepository;
    private final PostRepository postRepository;
    private final CommentRepository parentCommentRepository;
    private final CommentRepository repliesRepository;

    public CommentService(CommentRepository repository, ProfileRepository authorRepository, PostRepository postRepository, CommentRepository parentCommentRepository, CommentRepository repliesRepository)
    {
        super(repository);
        this.commentRepository = repository;
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
        this.parentCommentRepository = parentCommentRepository;
        this.repliesRepository = repliesRepository;
    }

    @Override
    public Comment save(Comment comment) {
    // ---------- OneToMany ----------
        if (comment.getReplies() != null) {
            List<Comment> managedReplies = new ArrayList<>();
            for (Comment item : comment.getReplies()) {
                if (item.getId() != null) {
                    Comment existingItem = repliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));

                     existingItem.setParentComment(comment);
                     managedReplies.add(existingItem);
                } else {
                    item.setParentComment(comment);
                    managedReplies.add(item);
                }
            }
            comment.setReplies(managedReplies);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (comment.getAuthor() != null) {
            if (comment.getAuthor().getId() != null) {
                Profile existingAuthor = authorRepository.findById(
                    comment.getAuthor().getId()
                ).orElseThrow(() -> new RuntimeException("Profile not found with id "
                    + comment.getAuthor().getId()));
                comment.setAuthor(existingAuthor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Profile newAuthor = authorRepository.save(comment.getAuthor());
                comment.setAuthor(newAuthor);
            }
        }
        
        if (comment.getPost() != null) {
            if (comment.getPost().getId() != null) {
                Post existingPost = postRepository.findById(
                    comment.getPost().getId()
                ).orElseThrow(() -> new RuntimeException("Post not found with id "
                    + comment.getPost().getId()));
                comment.setPost(existingPost);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Post newPost = postRepository.save(comment.getPost());
                comment.setPost(newPost);
            }
        }
        
        if (comment.getParentComment() != null) {
            if (comment.getParentComment().getId() != null) {
                Comment existingParentComment = parentCommentRepository.findById(
                    comment.getParentComment().getId()
                ).orElseThrow(() -> new RuntimeException("Comment not found with id "
                    + comment.getParentComment().getId()));
                comment.setParentComment(existingParentComment);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Comment newParentComment = parentCommentRepository.save(comment.getParentComment());
                comment.setParentComment(newParentComment);
            }
        }
        
    // ---------- OneToOne ----------
    return commentRepository.save(comment);
}


    public Comment update(Long id, Comment commentRequest) {
        Comment existing = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comment not found"));

    // Copier les champs simples
        existing.setContent(commentRequest.getContent());
        existing.setCommentDate(commentRequest.getCommentDate());

    // ---------- Relations ManyToOne ----------
        if (commentRequest.getAuthor() != null &&
            commentRequest.getAuthor().getId() != null) {

            Profile existingAuthor = authorRepository.findById(
                commentRequest.getAuthor().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            existing.setAuthor(existingAuthor);
        } else {
            existing.setAuthor(null);
        }
        
        if (commentRequest.getPost() != null &&
            commentRequest.getPost().getId() != null) {

            Post existingPost = postRepository.findById(
                commentRequest.getPost().getId()
            ).orElseThrow(() -> new RuntimeException("Post not found"));

            existing.setPost(existingPost);
        } else {
            existing.setPost(null);
        }
        
        if (commentRequest.getParentComment() != null &&
            commentRequest.getParentComment().getId() != null) {

            Comment existingParentComment = parentCommentRepository.findById(
                commentRequest.getParentComment().getId()
            ).orElseThrow(() -> new RuntimeException("Comment not found"));

            existing.setParentComment(existingParentComment);
        } else {
            existing.setParentComment(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getReplies().clear();

        if (commentRequest.getReplies() != null) {
            for (var item : commentRequest.getReplies()) {
                Comment existingItem;
                if (item.getId() != null) {
                    existingItem = repliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Comment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setParentComment(existing);
                existing.getReplies().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return commentRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Comment> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Comment entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getReplies() != null) {
            for (var child : entity.getReplies()) {
                // retirer la référence inverse
                child.setParentComment(null);
            }
            entity.getReplies().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getAuthor() != null) {
            entity.setAuthor(null);
        }
        
        if (entity.getPost() != null) {
            entity.setPost(null);
        }
        
        if (entity.getParentComment() != null) {
            entity.setParentComment(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Comment> saveAll(List<Comment> commentList) {

        return commentRepository.saveAll(commentList);
    }

}