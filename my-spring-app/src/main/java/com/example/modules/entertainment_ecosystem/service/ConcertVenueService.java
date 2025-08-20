package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ConcertVenue;
import com.example.modules.entertainment_ecosystem.repository.ConcertVenueRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ConcertVenueService extends BaseService<ConcertVenue> {

    protected final ConcertVenueRepository concertvenueRepository;
    private final LiveEventRepository concertsRepository;

    public ConcertVenueService(ConcertVenueRepository repository, LiveEventRepository concertsRepository)
    {
        super(repository);
        this.concertvenueRepository = repository;
        this.concertsRepository = concertsRepository;
    }

    @Override
    public ConcertVenue save(ConcertVenue concertvenue) {
    // ---------- OneToMany ----------
        if (concertvenue.getConcerts() != null) {
            List<LiveEvent> managedConcerts = new ArrayList<>();
            for (LiveEvent item : concertvenue.getConcerts()) {
                if (item.getId() != null) {
                    LiveEvent existingItem = concertsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));

                     existingItem.setVenue(concertvenue);
                     managedConcerts.add(existingItem);
                } else {
                    item.setVenue(concertvenue);
                    managedConcerts.add(item);
                }
            }
            concertvenue.setConcerts(managedConcerts);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return concertvenueRepository.save(concertvenue);
}


    public ConcertVenue update(Long id, ConcertVenue concertvenueRequest) {
        ConcertVenue existing = concertvenueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ConcertVenue not found"));

    // Copier les champs simples
        existing.setName(concertvenueRequest.getName());
        existing.setAddress(concertvenueRequest.getAddress());
        existing.setCapacity(concertvenueRequest.getCapacity());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getConcerts().clear();

        if (concertvenueRequest.getConcerts() != null) {
            for (var item : concertvenueRequest.getConcerts()) {
                LiveEvent existingItem;
                if (item.getId() != null) {
                    existingItem = concertsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setVenue(existing);
                existing.getConcerts().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return concertvenueRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ConcertVenue> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ConcertVenue entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getConcerts() != null) {
            for (var child : entity.getConcerts()) {
                
                child.setVenue(null); // retirer la référence inverse
                
            }
            entity.getConcerts().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}