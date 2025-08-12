package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.repository.EventTypeRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class EventTypeService extends BaseService<EventType> {

    protected final EventTypeRepository eventtypeRepository;

    public EventTypeService(EventTypeRepository repository)
    {
        super(repository);
        this.eventtypeRepository = repository;
    }

    @Override
    public EventType save(EventType eventtype) {

        if (eventtype.getEvents() != null) {
            for (LiveEvent item : eventtype.getEvents()) {
            item.setEventType(eventtype);
            }
        }

        return eventtypeRepository.save(eventtype);
    }


    public EventType update(Long id, EventType eventtypeRequest) {
        EventType existing = eventtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventType not found"));

    // Copier les champs simples
        existing.setName(eventtypeRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getEvents().clear();
        if (eventtypeRequest.getEvents() != null) {
            for (var item : eventtypeRequest.getEvents()) {
            item.setEventType(existing);
            existing.getEvents().add(item);
            }
        }

        return eventtypeRepository.save(existing);
    }
}