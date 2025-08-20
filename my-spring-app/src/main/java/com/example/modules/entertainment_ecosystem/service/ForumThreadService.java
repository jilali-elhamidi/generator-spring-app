package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;
import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.repository.ForumCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.ForumThreadTag;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadTagRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ForumThreadService extends BaseService<ForumThread> {

    protected final ForumThreadRepository forumthreadRepository;
    private final UserProfileRepository authorRepository;
    private final ForumPostRepository forumPostsRepository;
    private final ForumCategoryRepository categoryRepository;
    private final ForumThreadTagRepository tagsRepository;

    public ForumThreadService(ForumThreadRepository repository, UserProfileRepository authorRepository, ForumPostRepository forumPostsRepository, ForumCategoryRepository categoryRepository, ForumThreadTagRepository tagsRepository)
    {
        super(repository);
        this.forumthreadRepository = repository;
        this.authorRepository = authorRepository;
        this.forumPostsRepository = forumPostsRepository;
        this.categoryRepository = categoryRepository;
        this.tagsRepository = tagsRepository;
    }

    @Override
    public ForumThread save(ForumThread forumthread) {
    // ---------- OneToMany ----------
        if (forumthread.getForumPosts() != null) {
            List<ForumPost> managedForumPosts = new ArrayList<>();
            for (ForumPost item : forumthread.getForumPosts()) {
                if (item.getId() != null) {
                    ForumPost existingItem = forumPostsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumPost not found"));

                     existingItem.setThread(forumthread);
                     managedForumPosts.add(existingItem);
                } else {
                    item.setThread(forumthread);
                    managedForumPosts.add(item);
                }
            }
            forumthread.setForumPosts(managedForumPosts);
        }
    
    // ---------- ManyToMany ----------
        if (forumthread.getTags() != null &&
            !forumthread.getTags().isEmpty()) {

            List<ForumThreadTag> attachedTags = forumthread.getTags().stream()
            .map(item -> tagsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("ForumThreadTag not found with id " + item.getId())))
            .toList();

            forumthread.setTags(attachedTags);

            // côté propriétaire (ForumThreadTag → ForumThread)
            attachedTags.forEach(it -> it.getThreads().add(forumthread));
        }
        
    // ---------- ManyToOne ----------
        if (forumthread.getAuthor() != null &&
            forumthread.getAuthor().getId() != null) {

            UserProfile existingAuthor = authorRepository.findById(
                forumthread.getAuthor().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            forumthread.setAuthor(existingAuthor);
        }
        
        if (forumthread.getCategory() != null &&
            forumthread.getCategory().getId() != null) {

            ForumCategory existingCategory = categoryRepository.findById(
                forumthread.getCategory().getId()
            ).orElseThrow(() -> new RuntimeException("ForumCategory not found"));

            forumthread.setCategory(existingCategory);
        }
        
    // ---------- OneToOne ----------

    return forumthreadRepository.save(forumthread);
}


    public ForumThread update(Long id, ForumThread forumthreadRequest) {
        ForumThread existing = forumthreadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumThread not found"));

    // Copier les champs simples
        existing.setTitle(forumthreadRequest.getTitle());
        existing.setCreationDate(forumthreadRequest.getCreationDate());
        existing.setLastPostDate(forumthreadRequest.getLastPostDate());

    // ---------- Relations ManyToOne ----------
        if (forumthreadRequest.getAuthor() != null &&
            forumthreadRequest.getAuthor().getId() != null) {

            UserProfile existingAuthor = authorRepository.findById(
                forumthreadRequest.getAuthor().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setAuthor(existingAuthor);
        } else {
            existing.setAuthor(null);
        }
        
        if (forumthreadRequest.getCategory() != null &&
            forumthreadRequest.getCategory().getId() != null) {

            ForumCategory existingCategory = categoryRepository.findById(
                forumthreadRequest.getCategory().getId()
            ).orElseThrow(() -> new RuntimeException("ForumCategory not found"));

            existing.setCategory(existingCategory);
        } else {
            existing.setCategory(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (forumthreadRequest.getTags() != null) {
            existing.getTags().clear();

            List<ForumThreadTag> tagsList = forumthreadRequest.getTags().stream()
                .map(item -> tagsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ForumThreadTag not found")))
                .collect(Collectors.toList());

            existing.getTags().addAll(tagsList);

            // Mettre à jour le côté inverse
            tagsList.forEach(it -> {
                if (!it.getThreads().contains(existing)) {
                    it.getThreads().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getForumPosts().clear();

        if (forumthreadRequest.getForumPosts() != null) {
            for (var item : forumthreadRequest.getForumPosts()) {
                ForumPost existingItem;
                if (item.getId() != null) {
                    existingItem = forumPostsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumPost not found"));
                } else {
                existingItem = item;
                }

                existingItem.setThread(existing);
                existing.getForumPosts().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return forumthreadRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ForumThread> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ForumThread entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getForumPosts() != null) {
            for (var child : entity.getForumPosts()) {
                
                child.setThread(null); // retirer la référence inverse
                
            }
            entity.getForumPosts().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getTags() != null) {
            for (ForumThreadTag item : new ArrayList<>(entity.getTags())) {
                
                item.getThreads().remove(entity); // retire côté inverse
                
            }
            entity.getTags().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getAuthor() != null) {
            entity.setAuthor(null);
        }
        
        if (entity.getCategory() != null) {
            entity.setCategory(null);
        }
        
        repository.delete(entity);
        return true;
    }
}