package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ConcertVenue;
import com.example.modules.entertainment_ecosystem.repository.ConcertVenueRepository;

import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ConcertVenueService extends BaseService<ConcertVenue> {

    protected final ConcertVenueRepository concertvenueRepository;
    
    protected final LiveEventRepository concertsRepository;
    

    public ConcertVenueService(ConcertVenueRepository repository, LiveEventRepository concertsRepository)
    {
        super(repository);
        this.concertvenueRepository = repository;
        
        this.concertsRepository = concertsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public ConcertVenue update(Long id, ConcertVenue concertvenueRequest) {
        ConcertVenue existing = concertvenueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ConcertVenue not found"));

    // Copier les champs simples
        existing.setName(concertvenueRequest.getName());
        existing.setAddress(concertvenueRequest.getAddress());
        existing.setCapacity(concertvenueRequest.getCapacity());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<ConcertVenue> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ConcertVenue> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ConcertVenue.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ConcertVenue> saveAll(List<ConcertVenue> concertvenueList) {
        return super.saveAll(concertvenueList);
    }

}