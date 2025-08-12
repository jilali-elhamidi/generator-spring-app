package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.repository.EventAudienceRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class EventAudienceService extends BaseService<EventAudience> {

    protected final EventAudienceRepository eventaudienceRepository;

    public EventAudienceService(EventAudienceRepository repository)
    {
        super(repository);
        this.eventaudienceRepository = repository;
    }

    @Override
    public EventAudience save(EventAudience eventaudience) {
        if (eventaudience.getEvent() != null) {
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

        return eventaudienceRepository.save(existing);
    }
}