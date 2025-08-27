package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;

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
public class ContentRatingBoardService extends BaseService<ContentRatingBoard> {

    protected final ContentRatingBoardRepository contentratingboardRepository;
    
    protected final ContentRatingRepository ratingsRepository;
    

    public ContentRatingBoardService(ContentRatingBoardRepository repository, ContentRatingRepository ratingsRepository)
    {
        super(repository);
        this.contentratingboardRepository = repository;
        
        this.ratingsRepository = ratingsRepository;
        
    }

    @Transactional
    @Override
    public ContentRatingBoard save(ContentRatingBoard contentratingboard) {
    // ---------- OneToMany ----------
        if (contentratingboard.getRatings() != null) {
            List<ContentRating> managedRatings = new ArrayList<>();
            for (ContentRating item : contentratingboard.getRatings()) {
                if (item.getId() != null) {
                    ContentRating existingItem = ratingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentRating not found"));

                     existingItem.setBoard(contentratingboard);
                     managedRatings.add(existingItem);
                } else {
                    item.setBoard(contentratingboard);
                    managedRatings.add(item);
                }
            }
            contentratingboard.setRatings(managedRatings);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return contentratingboardRepository.save(contentratingboard);
}

    @Transactional
    @Override
    public ContentRatingBoard update(Long id, ContentRatingBoard contentratingboardRequest) {
        ContentRatingBoard existing = contentratingboardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));

    // Copier les champs simples
        existing.setName(contentratingboardRequest.getName());
        existing.setCountry(contentratingboardRequest.getCountry());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getRatings().clear();

        if (contentratingboardRequest.getRatings() != null) {
            for (var item : contentratingboardRequest.getRatings()) {
                ContentRating existingItem;
                if (item.getId() != null) {
                    existingItem = ratingsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ContentRating not found"));
                } else {
                existingItem = item;
                }

                existingItem.setBoard(existing);
                existing.getRatings().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return contentratingboardRepository.save(existing);
}

    // Pagination simple
    public Page<ContentRatingBoard> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ContentRatingBoard> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ContentRatingBoard.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ContentRatingBoard> saveAll(List<ContentRatingBoard> contentratingboardList) {
        return super.saveAll(contentratingboardList);
    }

}