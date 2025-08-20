package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Tour;
import com.example.modules.entertainment_ecosystem.repository.TourRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TourService extends BaseService<Tour> {

    protected final TourRepository tourRepository;
    private final ArtistRepository artistRepository;
    private final LiveEventRepository liveEventsRepository;

    public TourService(TourRepository repository,ArtistRepository artistRepository,LiveEventRepository liveEventsRepository)
    {
        super(repository);
        this.tourRepository = repository;
        this.artistRepository = artistRepository;
        this.liveEventsRepository = liveEventsRepository;
    }

    @Override
    public Tour save(Tour tour) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (tour.getLiveEvents() != null) {
            List<LiveEvent> managedLiveEvents = new ArrayList<>();
            for (LiveEvent item : tour.getLiveEvents()) {
            if (item.getId() != null) {
            LiveEvent existingItem = liveEventsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTour(tour);
            managedLiveEvents.add(existingItem);
            } else {
            item.setTour(tour);
            managedLiveEvents.add(item);
            }
            }
            tour.setLiveEvents(managedLiveEvents);
            }
        
    


    

    

    if (tour.getArtist() != null
        && tour.getArtist().getId() != null) {
        Artist existingArtist = artistRepository.findById(
        tour.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        tour.setArtist(existingArtist);
        }
    
    

        return tourRepository.save(tour);
    }


    public Tour update(Long id, Tour tourRequest) {
        Tour existing = tourRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tour not found"));

    // Copier les champs simples
        existing.setName(tourRequest.getName());
        existing.setStartDate(tourRequest.getStartDate());
        existing.setEndDate(tourRequest.getEndDate());

// Relations ManyToOne : mise à jour conditionnelle
        if (tourRequest.getArtist() != null &&
        tourRequest.getArtist().getId() != null) {

        Artist existingArtist = artistRepository.findById(
        tourRequest.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));

        existing.setArtist(existingArtist);
        } else {
        existing.setArtist(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getLiveEvents().clear();

        if (tourRequest.getLiveEvents() != null) {
        for (var item : tourRequest.getLiveEvents()) {
        LiveEvent existingItem;
        if (item.getId() != null) {
        existingItem = liveEventsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setTour(existing);

        // Ajouter directement dans la collection existante
        existing.getLiveEvents().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    


        return tourRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Tour> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Tour entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    
        if (entity.getLiveEvents() != null) {
        for (var child : entity.getLiveEvents()) {
        
            child.setTour(null); // retirer la référence inverse
        
        }
        entity.getLiveEvents().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getArtist() != null) {
        entity.setArtist(null);
        }
    

    


repository.delete(entity);
return true;
}
}