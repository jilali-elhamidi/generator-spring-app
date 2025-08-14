package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.repository.EventTypeRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EventTypeService extends BaseService<EventType> {

    protected final EventTypeRepository eventtypeRepository;
    private final LiveEventRepository eventsRepository;

    public EventTypeService(EventTypeRepository repository,LiveEventRepository eventsRepository)
    {
        super(repository);
        this.eventtypeRepository = repository;
        this.eventsRepository = eventsRepository;
    }

    @Override
    public EventType save(EventType eventtype) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (eventtype.getEvents() != null) {
            List<LiveEvent> managedEvents = new ArrayList<>();
            for (LiveEvent item : eventtype.getEvents()) {
            if (item.getId() != null) {
            LiveEvent existingItem = eventsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setEventType(eventtype);
            managedEvents.add(existingItem);
            } else {
            item.setEventType(eventtype);
            managedEvents.add(item);
            }
            }
            eventtype.setEvents(managedEvents);
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
        List<LiveEvent> managedEvents = new ArrayList<>();

        for (var item : eventtypeRequest.getEvents()) {
        if (item.getId() != null) {
        LiveEvent existingItem = eventsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        existingItem.setEventType(existing);
        managedEvents.add(existingItem);
        } else {
        item.setEventType(existing);
        managedEvents.add(item);
        }
        }
        existing.setEvents(managedEvents);
        }

    


        return eventtypeRepository.save(existing);
    }


}