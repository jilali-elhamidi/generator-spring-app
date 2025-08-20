package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumThreadTag;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadTagRepository;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ForumThreadTagService extends BaseService<ForumThreadTag> {

    protected final ForumThreadTagRepository forumthreadtagRepository;
    private final ForumThreadRepository threadsRepository;

    public ForumThreadTagService(ForumThreadTagRepository repository,ForumThreadRepository threadsRepository)
    {
        super(repository);
        this.forumthreadtagRepository = repository;
        this.threadsRepository = threadsRepository;
    }

    @Override
    public ForumThreadTag save(ForumThreadTag forumthreadtag) {


    


    
        if (forumthreadtag.getThreads() != null
        && !forumthreadtag.getThreads().isEmpty()) {

        List<ForumThread> attachedThreads = forumthreadtag.getThreads().stream()
        .map(item -> threadsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumThread not found with id " + item.getId())))
        .toList();

        forumthreadtag.setThreads(attachedThreads);

        // côté propriétaire (ForumThread → ForumThreadTag)
        attachedThreads.forEach(it -> it.getTags().add(forumthreadtag));
        }
    

    

        return forumthreadtagRepository.save(forumthreadtag);
    }


    public ForumThreadTag update(Long id, ForumThreadTag forumthreadtagRequest) {
        ForumThreadTag existing = forumthreadtagRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumThreadTag not found"));

    // Copier les champs simples
        existing.setName(forumthreadtagRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée
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

// Relations OneToMany : synchronisation sécurisée

    


        return forumthreadtagRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<ForumThreadTag> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

ForumThreadTag entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    
        if (entity.getThreads() != null) {
        for (ForumThread item : new ArrayList<>(entity.getThreads())) {
        
            item.getTags().remove(entity); // retire côté inverse
        
        }
        entity.getThreads().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}