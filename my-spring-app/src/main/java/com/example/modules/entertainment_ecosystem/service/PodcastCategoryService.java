package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import com.example.modules.entertainment_ecosystem.repository.PodcastCategoryRepository;

import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PodcastCategoryService extends BaseService<PodcastCategory> {

    protected final PodcastCategoryRepository podcastcategoryRepository;
    
    protected final PodcastRepository podcastsRepository;
    

    public PodcastCategoryService(PodcastCategoryRepository repository, PodcastRepository podcastsRepository)
    {
        super(repository);
        this.podcastcategoryRepository = repository;
        
        this.podcastsRepository = podcastsRepository;
        
    }

    @Transactional
    @Override
    public PodcastCategory save(PodcastCategory podcastcategory) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (podcastcategory.getPodcasts() != null &&
            !podcastcategory.getPodcasts().isEmpty()) {

            List<Podcast> attachedPodcasts = new ArrayList<>();
            for (Podcast item : podcastcategory.getPodcasts()) {
                if (item.getId() != null) {
                    Podcast existingItem = podcastsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Podcast not found with id " + item.getId()));
                    attachedPodcasts.add(existingItem);
                } else {

                    Podcast newItem = podcastsRepository.save(item);
                    attachedPodcasts.add(newItem);
                }
            }

            podcastcategory.setPodcasts(attachedPodcasts);

            // côté propriétaire (Podcast → PodcastCategory)
            attachedPodcasts.forEach(it -> it.getCategories().add(podcastcategory));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return podcastcategoryRepository.save(podcastcategory);
}

    @Transactional
    @Override
    public PodcastCategory update(Long id, PodcastCategory podcastcategoryRequest) {
        PodcastCategory existing = podcastcategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PodcastCategory not found"));

    // Copier les champs simples
        existing.setName(podcastcategoryRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return podcastcategoryRepository.save(existing);
}

    // Pagination simple
    public Page<PodcastCategory> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<PodcastCategory> search(Map<String, String> filters, Pageable pageable) {
        return super.search(PodcastCategory.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<PodcastCategory> saveAll(List<PodcastCategory> podcastcategoryList) {
        return super.saveAll(podcastcategoryList);
    }

}