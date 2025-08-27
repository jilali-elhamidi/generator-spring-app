package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.repository.EventAudienceRepository;

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
public class EventAudienceService extends BaseService<EventAudience> {

    protected final EventAudienceRepository eventaudienceRepository;
    
    protected final LiveEventRepository eventRepository;
    

    public EventAudienceService(EventAudienceRepository repository, LiveEventRepository eventRepository)
    {
        super(repository);
        this.eventaudienceRepository = repository;
        
        this.eventRepository = eventRepository;
        
    }

    @Transactional
    @Override
    public EventAudience save(EventAudience eventaudience) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (eventaudience.getEvent() != null) {
            if (eventaudience.getEvent().getId() != null) {
                LiveEvent existingEvent = eventRepository.findById(eventaudience.getEvent().getId())
                    .orElseThrow(() -> new RuntimeException("LiveEvent not found with id "
                        + eventaudience.getEvent().getId()));
                eventaudience.setEvent(existingEvent);
            } else {
                // Nouvel objet → sauvegarde d'abord
                LiveEvent newEvent = eventRepository.save(eventaudience.getEvent());
                eventaudience.setEvent(newEvent);
            }

            eventaudience.getEvent().setAudience(eventaudience);
        }
        
    return eventaudienceRepository.save(eventaudience);
}

    @Transactional
    @Override
    public EventAudience update(Long id, EventAudience eventaudienceRequest) {
        EventAudience existing = eventaudienceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventAudience not found"));

    // Copier les champs simples
        existing.setCount(eventaudienceRequest.getCount());
        existing.setAudienceType(eventaudienceRequest.getAudienceType());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (eventaudienceRequest.getEvent() != null &&eventaudienceRequest.getEvent().getId() != null) {

        LiveEvent event = eventRepository.findById(eventaudienceRequest.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("LiveEvent not found"));

        existing.setEvent(event);
        event.setAudience(existing);
        }
    
    return eventaudienceRepository.save(existing);
}

    // Pagination simple
    public Page<EventAudience> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<EventAudience> search(Map<String, String> filters, Pageable pageable) {
        return super.search(EventAudience.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<EventAudience> saveAll(List<EventAudience> eventaudienceList) {
        return super.saveAll(eventaudienceList);
    }

}