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

    public EventTypeService(EventTypeRepository repository, LiveEventRepository eventsRepository)
    {
        super(repository);
        this.eventtypeRepository = repository;
        this.eventsRepository = eventsRepository;
    }

    @Override
    public EventType save(EventType eventtype) {
    // ---------- OneToMany ----------
        if (eventtype.getEvents() != null) {
            List<LiveEvent> managedEvents = new ArrayList<>();
            for (LiveEvent item : eventtype.getEvents()) {
                if (item.getId() != null) {
                    LiveEvent existingItem = eventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));

                     existingItem.setEventType(eventtype);
                     managedEvents.add(existingItem);
                } else {
                    item.setEventType(eventtype);
                    managedEvents.add(item);
                }
            }
            eventtype.setEvents(managedEvents);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return eventtypeRepository.save(eventtype);
}


    public EventType update(Long id, EventType eventtypeRequest) {
        EventType existing = eventtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventType not found"));

    // Copier les champs simples
        existing.setName(eventtypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getEvents().clear();

        if (eventtypeRequest.getEvents() != null) {
            for (var item : eventtypeRequest.getEvents()) {
                LiveEvent existingItem;
                if (item.getId() != null) {
                    existingItem = eventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setEventType(existing);
                existing.getEvents().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

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