package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ContentRatingAgeGroup;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingAgeGroupRepository;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.repository.ContentRatingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContentRatingAgeGroupService extends BaseService<ContentRatingAgeGroup> {

    protected final ContentRatingAgeGroupRepository contentratingagegroupRepository;
    private final ContentRatingRepository contentRatingsRepository;

    public ContentRatingAgeGroupService(ContentRatingAgeGroupRepository repository,ContentRatingRepository contentRatingsRepository)
    {
        super(repository);
        this.contentratingagegroupRepository = repository;
        this.contentRatingsRepository = contentRatingsRepository;
    }

    @Override
    public ContentRatingAgeGroup save(ContentRatingAgeGroup contentratingagegroup) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (contentratingagegroup.getContentRatings() != null) {
            List<ContentRating> managedContentRatings = new ArrayList<>();
            for (ContentRating item : contentratingagegroup.getContentRatings()) {
            if (item.getId() != null) {
            ContentRating existingItem = contentRatingsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ContentRating not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAgeGroup(contentratingagegroup);
            managedContentRatings.add(existingItem);
            } else {
            item.setAgeGroup(contentratingagegroup);
            managedContentRatings.add(item);
            }
            }
            contentratingagegroup.setContentRatings(managedContentRatings);
            }
        
    


    

    

        return contentratingagegroupRepository.save(contentratingagegroup);
    }


    public ContentRatingAgeGroup update(Long id, ContentRatingAgeGroup contentratingagegroupRequest) {
        ContentRatingAgeGroup existing = contentratingagegroupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ContentRatingAgeGroup not found"));

    // Copier les champs simples
        existing.setAgeGroup(contentratingagegroupRequest.getAgeGroup());
        existing.setDescription(contentratingagegroupRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getContentRatings().clear();

        if (contentratingagegroupRequest.getContentRatings() != null) {
        for (var item : contentratingagegroupRequest.getContentRatings()) {
        ContentRating existingItem;
        if (item.getId() != null) {
        existingItem = contentRatingsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ContentRating not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAgeGroup(existing);

        // Ajouter directement dans la collection existante
        existing.getContentRatings().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return contentratingagegroupRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<ContentRatingAgeGroup> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

ContentRatingAgeGroup entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getContentRatings() != null) {
        for (var child : entity.getContentRatings()) {
        
            child.setAgeGroup(null); // retirer la référence inverse
        
        }
        entity.getContentRatings().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}