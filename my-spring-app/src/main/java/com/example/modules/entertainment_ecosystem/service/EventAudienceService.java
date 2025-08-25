package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.repository.EventAudienceRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EventAudienceService extends BaseService<EventAudience> {

    protected final EventAudienceRepository eventaudienceRepository;
    private final LiveEventRepository eventRepository;

    public EventAudienceService(EventAudienceRepository repository, LiveEventRepository eventRepository)
    {
        super(repository);
        this.eventaudienceRepository = repository;
        this.eventRepository = eventRepository;
    }

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


    public EventAudience update(Long id, EventAudience eventaudienceRequest) {
        EventAudience existing = eventaudienceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventAudience not found"));

    // Copier les champs simples
        existing.setCount(eventaudienceRequest.getCount());
        existing.setAudienceType(eventaudienceRequest.getAudienceType());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<EventAudience> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        EventAudience entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
        if (entity.getEvent() != null) {
            entity.getEvent().setAudience(null);
            entity.setEvent(null);
        }
        
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<EventAudience> saveAll(List<EventAudience> eventaudienceList) {

        return eventaudienceRepository.saveAll(eventaudienceList);
    }

}