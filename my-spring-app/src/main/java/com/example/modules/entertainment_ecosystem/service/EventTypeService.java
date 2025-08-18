package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.repository.EventTypeRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        // Vider la collection existante
        existing.getEvents().clear();

        if (eventtypeRequest.getEvents() != null) {
        for (var item : eventtypeRequest.getEvents()) {
        LiveEvent existingItem;
        if (item.getId() != null) {
        existingItem = eventsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setEventType(existing);

        // Ajouter directement dans la collection existante
        existing.getEvents().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return eventtypeRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<EventType> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

EventType entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getEvents() != null) {
        for (var child : entity.getEvents()) {
        
            child.setEventType(null); // retirer la référence inverse
        
        }
        entity.getEvents().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}