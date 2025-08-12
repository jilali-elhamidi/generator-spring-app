package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ForumPostService extends BaseService<ForumPost> {

    protected final ForumPostRepository forumpostRepository;
    private final UserProfileRepository authorRepository;
    private final ForumThreadRepository threadRepository;
    private final ForumPostRepository parentPostRepository;

    public ForumPostService(ForumPostRepository repository,UserProfileRepository authorRepository,ForumThreadRepository threadRepository,ForumPostRepository parentPostRepository)
    {
        super(repository);
        this.forumpostRepository = repository;
        this.authorRepository = authorRepository;
        this.threadRepository = threadRepository;
        this.parentPostRepository = parentPostRepository;
    }

    @Override
    public ForumPost save(ForumPost forumpost) {

        if (forumpost.getAuthor() != null && forumpost.getAuthor().getId() != null) {
        UserProfile author = authorRepository.findById(forumpost.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        forumpost.setAuthor(author);
        }

        if (forumpost.getThread() != null && forumpost.getThread().getId() != null) {
        ForumThread thread = threadRepository.findById(forumpost.getThread().getId())
                .orElseThrow(() -> new RuntimeException("ForumThread not found"));
        forumpost.setThread(thread);
        }

        if (forumpost.getParentPost() != null && forumpost.getParentPost().getId() != null) {
        ForumPost parentPost = parentPostRepository.findById(forumpost.getParentPost().getId())
                .orElseThrow(() -> new RuntimeException("ForumPost not found"));
        forumpost.setParentPost(parentPost);
        }

        if (forumpost.getReplies() != null) {
            for (ForumPost item : forumpost.getReplies()) {
            item.setParentPost(forumpost);
            }
        }

        return forumpostRepository.save(forumpost);
    }


    public ForumPost update(Long id, ForumPost forumpostRequest) {
        ForumPost existing = forumpostRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumPost not found"));

    // Copier les champs simples
        existing.setContent(forumpostRequest.getContent());
        existing.setPostDate(forumpostRequest.getPostDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (forumpostRequest.getAuthor() != null && forumpostRequest.getAuthor().getId() != null) {
        UserProfile author = authorRepository.findById(forumpostRequest.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setAuthor(author);
        }

        if (forumpostRequest.getThread() != null && forumpostRequest.getThread().getId() != null) {
        ForumThread thread = threadRepository.findById(forumpostRequest.getThread().getId())
                .orElseThrow(() -> new RuntimeException("ForumThread not found"));
        existing.setThread(thread);
        }

        if (forumpostRequest.getParentPost() != null && forumpostRequest.getParentPost().getId() != null) {
        ForumPost parentPost = parentPostRepository.findById(forumpostRequest.getParentPost().getId())
                .orElseThrow(() -> new RuntimeException("ForumPost not found"));
        existing.setParentPost(parentPost);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getReplies().clear();
        if (forumpostRequest.getReplies() != null) {
            for (var item : forumpostRequest.getReplies()) {
            item.setParentPost(existing);
            existing.getReplies().add(item);
            }
        }

        return forumpostRepository.save(existing);
    }
}