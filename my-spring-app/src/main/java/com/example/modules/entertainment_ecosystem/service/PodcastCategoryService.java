package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import com.example.modules.entertainment_ecosystem.repository.PodcastCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PodcastCategoryService extends BaseService<PodcastCategory> {

    protected final PodcastCategoryRepository podcastcategoryRepository;
    private final PodcastRepository podcastsRepository;

    public PodcastCategoryService(PodcastCategoryRepository repository,PodcastRepository podcastsRepository)
    {
        super(repository);
        this.podcastcategoryRepository = repository;
        this.podcastsRepository = podcastsRepository;
    }

    @Override
    public PodcastCategory save(PodcastCategory podcastcategory) {


    


    
        if (podcastcategory.getPodcasts() != null
        && !podcastcategory.getPodcasts().isEmpty()) {

        List<Podcast> attachedPodcasts = podcastcategory.getPodcasts().stream()
        .map(item -> podcastsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Podcast not found with id " + item.getId())))
        .toList();

        podcastcategory.setPodcasts(attachedPodcasts);

        // côté propriétaire (Podcast → PodcastCategory)
        attachedPodcasts.forEach(it -> it.getCategories().add(podcastcategory));
        }
    

    

        return podcastcategoryRepository.save(podcastcategory);
    }


    public PodcastCategory update(Long id, PodcastCategory podcastcategoryRequest) {
        PodcastCategory existing = podcastcategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PodcastCategory not found"));

    // Copier les champs simples
        existing.setName(podcastcategoryRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée
        if (podcastcategoryRequest.getPodcasts() != null) {
        existing.getPodcasts().clear();

        List<Podcast> podcastsList = podcastcategoryRequest.getPodcasts().stream()
        .map(item -> podcastsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Podcast not found")))
        .collect(Collectors.toList());

        existing.getPodcasts().addAll(podcastsList);

        // Mettre à jour le côté inverse
        podcastsList.forEach(it -> {
        if (!it.getCategories().contains(existing)) {
        it.getCategories().add(existing);
        }
        });
        }

// Relations OneToMany : synchronisation sécurisée

    


        return podcastcategoryRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<PodcastCategory> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

PodcastCategory entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    
        if (entity.getPodcasts() != null) {
        for (Podcast item : new ArrayList<>(entity.getPodcasts())) {
        
            item.getCategories().remove(entity); // retire côté inverse
        
        }
        entity.getPodcasts().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}