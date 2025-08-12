package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.ForumPost;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ForumThreadService extends BaseService<ForumThread> {

    protected final ForumThreadRepository forumthreadRepository;
    private final UserProfileRepository authorRepository;

    public ForumThreadService(ForumThreadRepository repository,UserProfileRepository authorRepository)
    {
        super(repository);
        this.forumthreadRepository = repository;
        this.authorRepository = authorRepository;
    }

    @Override
    public ForumThread save(ForumThread forumthread) {

        if (forumthread.getAuthor() != null && forumthread.getAuthor().getId() != null) {
        UserProfile author = authorRepository.findById(forumthread.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        forumthread.setAuthor(author);
        }

        if (forumthread.getForumPosts() != null) {
            for (ForumPost item : forumthread.getForumPosts()) {
            item.setThread(forumthread);
            }
        }

        return forumthreadRepository.save(forumthread);
    }


    public ForumThread update(Long id, ForumThread forumthreadRequest) {
        ForumThread existing = forumthreadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumThread not found"));

    // Copier les champs simples
        existing.setTitle(forumthreadRequest.getTitle());
        existing.setCreationDate(forumthreadRequest.getCreationDate());
        existing.setLastPostDate(forumthreadRequest.getLastPostDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (forumthreadRequest.getAuthor() != null && forumthreadRequest.getAuthor().getId() != null) {
        UserProfile author = authorRepository.findById(forumthreadRequest.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setAuthor(author);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getForumPosts().clear();
        if (forumthreadRequest.getForumPosts() != null) {
            for (var item : forumthreadRequest.getForumPosts()) {
            item.setThread(existing);
            existing.getForumPosts().add(item);
            }
        }

        return forumthreadRepository.save(existing);
    }
}