package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.repository.EventLocationRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class EventLocationService extends BaseService<EventLocation> {

    protected final EventLocationRepository eventlocationRepository;

    public EventLocationService(EventLocationRepository repository)
    {
        super(repository);
        this.eventlocationRepository = repository;
    }

    @Override
    public EventLocation save(EventLocation eventlocation) {

        if (eventlocation.getLiveEvents() != null) {
            for (LiveEvent item : eventlocation.getLiveEvents()) {
            item.setLocation(eventlocation);
            }
        }

        return eventlocationRepository.save(eventlocation);
    }


    public EventLocation update(Long id, EventLocation eventlocationRequest) {
        EventLocation existing = eventlocationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventLocation not found"));

    // Copier les champs simples
        existing.setName(eventlocationRequest.getName());
        existing.setAddress(eventlocationRequest.getAddress());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getLiveEvents().clear();
        if (eventlocationRequest.getLiveEvents() != null) {
            for (var item : eventlocationRequest.getLiveEvents()) {
            item.setLocation(existing);
            existing.getLiveEvents().add(item);
            }
        }

    


        return eventlocationRepository.save(existing);
    }
}