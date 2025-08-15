package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ForumPostService extends BaseService<ForumPost> {

    protected final ForumPostRepository forumpostRepository;
    private final UserProfileRepository authorRepository;
    private final ForumThreadRepository threadRepository;
    private final ForumPostRepository repliesRepository;
    private final ForumPostRepository parentPostRepository;

    public ForumPostService(ForumPostRepository repository,UserProfileRepository authorRepository,ForumThreadRepository threadRepository,ForumPostRepository repliesRepository,ForumPostRepository parentPostRepository)
    {
        super(repository);
        this.forumpostRepository = repository;
        this.authorRepository = authorRepository;
        this.threadRepository = threadRepository;
        this.repliesRepository = repliesRepository;
        this.parentPostRepository = parentPostRepository;
    }

    @Override
    public ForumPost save(ForumPost forumpost) {


    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (forumpost.getReplies() != null) {
            List<ForumPost> managedReplies = new ArrayList<>();
            for (ForumPost item : forumpost.getReplies()) {
            if (item.getId() != null) {
            ForumPost existingItem = repliesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ForumPost not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setParentPost(forumpost);
            managedReplies.add(existingItem);
            } else {
            item.setParentPost(forumpost);
            managedReplies.add(item);
            }
            }
            forumpost.setReplies(managedReplies);
            }
        
    

    

    if (forumpost.getAuthor() != null
        && forumpost.getAuthor().getId() != null) {
        UserProfile existingAuthor = authorRepository.findById(
        forumpost.getAuthor().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        forumpost.setAuthor(existingAuthor);
        }
    
    if (forumpost.getThread() != null
        && forumpost.getThread().getId() != null) {
        ForumThread existingThread = threadRepository.findById(
        forumpost.getThread().getId()
        ).orElseThrow(() -> new RuntimeException("ForumThread not found"));
        forumpost.setThread(existingThread);
        }
    
    
    if (forumpost.getParentPost() != null
        && forumpost.getParentPost().getId() != null) {
        ForumPost existingParentPost = parentPostRepository.findById(
        forumpost.getParentPost().getId()
        ).orElseThrow(() -> new RuntimeException("ForumPost not found"));
        forumpost.setParentPost(existingParentPost);
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
        if (forumpostRequest.getAuthor() != null &&
        forumpostRequest.getAuthor().getId() != null) {

        UserProfile existingAuthor = authorRepository.findById(
        forumpostRequest.getAuthor().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setAuthor(existingAuthor);
        } else {
        existing.setAuthor(null);
        }
        if (forumpostRequest.getThread() != null &&
        forumpostRequest.getThread().getId() != null) {

        ForumThread existingThread = threadRepository.findById(
        forumpostRequest.getThread().getId()
        ).orElseThrow(() -> new RuntimeException("ForumThread not found"));

        existing.setThread(existingThread);
        } else {
        existing.setThread(null);
        }
        if (forumpostRequest.getParentPost() != null &&
        forumpostRequest.getParentPost().getId() != null) {

        ForumPost existingParentPost = parentPostRepository.findById(
        forumpostRequest.getParentPost().getId()
        ).orElseThrow(() -> new RuntimeException("ForumPost not found"));

        existing.setParentPost(existingParentPost);
        } else {
        existing.setParentPost(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getReplies().clear();

        if (forumpostRequest.getReplies() != null) {
        for (var item : forumpostRequest.getReplies()) {
        ForumPost existingItem;
        if (item.getId() != null) {
        existingItem = repliesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumPost not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setParentPost(existing);

        // Ajouter directement dans la collection existante
        existing.getReplies().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    


        return forumpostRepository.save(existing);
    }


}