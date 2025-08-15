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

    public EventAudienceService(EventAudienceRepository repository,LiveEventRepository eventRepository)
    {
        super(repository);
        this.eventaudienceRepository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventAudience save(EventAudience eventaudience) {


    

    
        if (eventaudience.getEvent() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            eventaudience.setEvent(
            eventRepository.findById(eventaudience.getEvent().getId())
            .orElseThrow(() -> new RuntimeException("event not found"))
            );
        
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

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (eventaudienceRequest.getEvent() != null
        && eventaudienceRequest.getEvent().getId() != null) {

        LiveEvent event = eventRepository.findById(
        eventaudienceRequest.getEvent().getId()
        ).orElseThrow(() -> new RuntimeException("LiveEvent not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setEvent(event);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
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
        // Dissocier côté inverse automatiquement
        entity.getEvent().setAudience(null);
        // Dissocier côté direct
        entity.setEvent(null);
        }
    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}