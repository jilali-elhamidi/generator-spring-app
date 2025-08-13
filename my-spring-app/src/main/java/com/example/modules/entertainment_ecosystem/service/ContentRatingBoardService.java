package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingBoardRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRating;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ContentRatingBoardService extends BaseService<ContentRatingBoard> {

    protected final ContentRatingBoardRepository contentratingboardRepository;

    public ContentRatingBoardService(ContentRatingBoardRepository repository)
    {
        super(repository);
        this.contentratingboardRepository = repository;
    }

    @Override
    public ContentRatingBoard save(ContentRatingBoard contentratingboard) {

        if (contentratingboard.getRatings() != null) {
            for (ContentRating item : contentratingboard.getRatings()) {
            item.setBoard(contentratingboard);
            }
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
            for (var item : contentratingboardRequest.getRatings()) {
            item.setBoard(existing);
            existing.getRatings().add(item);
            }
        }

    


        return contentratingboardRepository.save(existing);
    }
}