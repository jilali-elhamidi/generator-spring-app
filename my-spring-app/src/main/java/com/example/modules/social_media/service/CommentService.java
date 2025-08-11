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

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class CommentService extends BaseService<Comment> {

    protected final CommentRepository commentRepository;
    private final ProfileRepository authorRepository;
    private final PostRepository postRepository;
    private final CommentRepository parentCommentRepository;

    public CommentService(CommentRepository repository,ProfileRepository authorRepository,PostRepository postRepository,CommentRepository parentCommentRepository)
    {
        super(repository);
        this.commentRepository = repository;
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
        this.parentCommentRepository = parentCommentRepository;
    }

    @Override
    public Comment save(Comment comment) {

        if (comment.getAuthor() != null && comment.getAuthor().getId() != null) {
        Profile author = authorRepository.findById(comment.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        comment.setAuthor(author);
        }

        if (comment.getPost() != null && comment.getPost().getId() != null) {
        Post post = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);
        }

        if (comment.getParentComment() != null && comment.getParentComment().getId() != null) {
        Comment parentComment = parentCommentRepository.findById(comment.getParentComment().getId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setParentComment(parentComment);
        }

        if (comment.getReplies() != null) {
            for (Comment item : comment.getReplies()) {
            item.setParentComment(comment);
            }
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

        if (commentRequest.getAuthor() != null && commentRequest.getAuthor().getId() != null) {
        Profile author = authorRepository.findById(commentRequest.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        existing.setAuthor(author);
        }

        if (commentRequest.getPost() != null && commentRequest.getPost().getId() != null) {
        Post post = postRepository.findById(commentRequest.getPost().getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        existing.setPost(post);
        }

        if (commentRequest.getParentComment() != null && commentRequest.getParentComment().getId() != null) {
        Comment parentComment = parentCommentRepository.findById(commentRequest.getParentComment().getId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        existing.setParentComment(parentComment);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getReplies().clear();
        if (commentRequest.getReplies() != null) {
            for (var item : commentRequest.getReplies()) {
            item.setParentComment(existing);
            existing.getReplies().add(item);
            }
        }

        return commentRepository.save(existing);
    }
}