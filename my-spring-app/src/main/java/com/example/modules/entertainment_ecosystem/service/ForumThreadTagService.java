package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumThreadTag;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadTagRepository;

import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ForumThreadTagService extends BaseService<ForumThreadTag> {

    protected final ForumThreadTagRepository forumthreadtagRepository;
    
    protected final ForumThreadRepository threadsRepository;
    

    public ForumThreadTagService(ForumThreadTagRepository repository, ForumThreadRepository threadsRepository)
    {
        super(repository);
        this.forumthreadtagRepository = repository;
        
        this.threadsRepository = threadsRepository;
        
    }

    @Transactional
    @Override
    public ForumThreadTag save(ForumThreadTag forumthreadtag) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (forumthreadtag.getThreads() != null &&
            !forumthreadtag.getThreads().isEmpty()) {

            List<ForumThread> attachedThreads = new ArrayList<>();
            for (ForumThread item : forumthreadtag.getThreads()) {
                if (item.getId() != null) {
                    ForumThread existingItem = threadsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumThread not found with id " + item.getId()));
                    attachedThreads.add(existingItem);
                } else {

                    ForumThread newItem = threadsRepository.save(item);
                    attachedThreads.add(newItem);
                }
            }

            forumthreadtag.setThreads(attachedThreads);

            // côté propriétaire (ForumThread → ForumThreadTag)
            attachedThreads.forEach(it -> it.getTags().add(forumthreadtag));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return forumthreadtagRepository.save(forumthreadtag);
}

    @Transactional
    @Override
    public ForumThreadTag update(Long id, ForumThreadTag forumthreadtagRequest) {
        ForumThreadTag existing = forumthreadtagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumThreadTag not found"));

    // Copier les champs simples
        existing.setName(forumthreadtagRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (forumthreadtagRequest.getThreads() != null) {
            existing.getThreads().clear();

            List<ForumThread> threadsList = forumthreadtagRequest.getThreads().stream()
                .map(item -> threadsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ForumThread not found")))
                .collect(Collectors.toList());

            existing.getThreads().addAll(threadsList);

            // Mettre à jour le côté inverse
            threadsList.forEach(it -> {
                if (!it.getTags().contains(existing)) {
                    it.getTags().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return forumthreadtagRepository.save(existing);
}

    // Pagination simple
    public Page<ForumThreadTag> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ForumThreadTag> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ForumThreadTag.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ForumThreadTag> saveAll(List<ForumThreadTag> forumthreadtagList) {
        return super.saveAll(forumthreadtagList);
    }

}