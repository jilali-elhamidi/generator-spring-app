package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContentRatingBoardService extends BaseService<ContentRatingBoard> {

    protected final ContentRatingBoardRepository contentratingboardRepository;
    private final ContentRatingRepository ratingsRepository;

    public ContentRatingBoardService(ContentRatingBoardRepository repository,ContentRatingRepository ratingsRepository)
    {
        super(repository);
        this.contentratingboardRepository = repository;
        this.ratingsRepository = ratingsRepository;
    }

    @Override
    public ContentRatingBoard save(ContentRatingBoard contentratingboard) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentratingboard.getRatings() != null) {
            List<ContentRating> managedRatings = new ArrayList<>();
            for (ContentRating item : contentratingboard.getRatings()) {
            if (item.getId() != null) {
            ContentRating existingItem = ratingsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ContentRating not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setBoard(contentratingboard);
            managedRatings.add(existingItem);
            } else {
            item.setBoard(contentratingboard);
            managedRatings.add(item);
            }
            }
            contentratingboard.setRatings(managedRatings);
            }
        
    

    

        return contentratingboardRepository.save(contentratingboard);
    }


    public ContentRatingBoard update(Long id, ContentRatingBoard contentratingboardRequest) {
        ContentRatingBoard existing = contentratingboardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentRatingBoard not found"));

    // Copier les champs simples
        existing.setName(contentratingboardRequest.getName());
        existing.setCountry(contentratingboardRequest.getCountry());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        existing.getRatings().clear();

        if (contentratingboardRequest.getRatings() != null) {
        List<ContentRating> managedRatings = new ArrayList<>();

        for (var item : contentratingboardRequest.getRatings()) {
        if (item.getId() != null) {
        ContentRating existingItem = ratingsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ContentRating not found"));
        existingItem.setBoard(existing);
        managedRatings.add(existingItem);
        } else {
        item.setBoard(existing);
        managedRatings.add(item);
        }
        }
        existing.setRatings(managedRatings);
        }

    


        return contentratingboardRepository.save(existing);
    }


}