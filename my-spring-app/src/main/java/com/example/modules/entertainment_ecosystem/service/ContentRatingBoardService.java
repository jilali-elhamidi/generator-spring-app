package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContentRatingBoardService extends BaseService<ContentRatingBoard> {

    protected final ContentRatingBoardRepository contentratingboardRepository;
    private final ContentRatingRepository ratingsRepository;

    public ContentRatingBoardService(ContentRatingBoardRepository repository, ContentRatingRepository ratingsRepository)
    {
        super(repository);
        this.contentratingboardRepository = repository;
        this.ratingsRepository = ratingsRepository;
    }

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


    public ContentRatingBoard update(Long id, ContentRatingBoard contentratingboardRequest) {
        ContentRatingBoard existing = contentratingboardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));

    // Copier les champs simples
        existing.setName(contentratingboardRequest.getName());
        existing.setCountry(contentratingboardRequest.getCountry());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ContentRatingBoard> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ContentRatingBoard entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getRatings() != null) {
            for (var child : entity.getRatings()) {
                // retirer la référence inverse
                child.setBoard(null);
            }
            entity.getRatings().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<ContentRatingBoard> saveAll(List<ContentRatingBoard> contentratingboardList) {

        return contentratingboardRepository.saveAll(contentratingboardList);
    }

}