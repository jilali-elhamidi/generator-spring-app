package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRatingAgeGroup;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingAgeGroupRepository;

import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ContentRatingAgeGroupService extends BaseService<ContentRatingAgeGroup> {

    protected final ContentRatingAgeGroupRepository contentratingagegroupRepository;
    
    protected final ContentRatingRepository contentRatingsRepository;
    

    public ContentRatingAgeGroupService(ContentRatingAgeGroupRepository repository, ContentRatingRepository contentRatingsRepository)
    {
        super(repository);
        this.contentratingagegroupRepository = repository;
        
        this.contentRatingsRepository = contentRatingsRepository;
        
    }

    @Transactional
    @Override
    public ContentRatingAgeGroup save(ContentRatingAgeGroup contentratingagegroup) {
    // ---------- OneToMany ----------
        if (contentratingagegroup.getContentRatings() != null) {
            List<ContentRating> managedContentRatings = new ArrayList<>();
            for (ContentRating item : contentratingagegroup.getContentRatings()) {
                if (item.getId() != null) {
                    ContentRating existingItem = contentRatingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentRating not found"));

                     existingItem.setAgeGroup(contentratingagegroup);
                     managedContentRatings.add(existingItem);
                } else {
                    item.setAgeGroup(contentratingagegroup);
                    managedContentRatings.add(item);
                }
            }
            contentratingagegroup.setContentRatings(managedContentRatings);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return contentratingagegroupRepository.save(contentratingagegroup);
}

    @Transactional
    @Override
    public ContentRatingAgeGroup update(Long id, ContentRatingAgeGroup contentratingagegroupRequest) {
        ContentRatingAgeGroup existing = contentratingagegroupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentRatingAgeGroup not found"));

    // Copier les champs simples
        existing.setAgeGroup(contentratingagegroupRequest.getAgeGroup());
        existing.setDescription(contentratingagegroupRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getContentRatings().clear();

        if (contentratingagegroupRequest.getContentRatings() != null) {
            for (var item : contentratingagegroupRequest.getContentRatings()) {
                ContentRating existingItem;
                if (item.getId() != null) {
                    existingItem = contentRatingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentRating not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAgeGroup(existing);
                existing.getContentRatings().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return contentratingagegroupRepository.save(existing);
}

    // Pagination simple
    public Page<ContentRatingAgeGroup> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ContentRatingAgeGroup> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ContentRatingAgeGroup.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ContentRatingAgeGroup> saveAll(List<ContentRatingAgeGroup> contentratingagegroupList) {
        return super.saveAll(contentratingagegroupList);
    }

}