package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.repository.EventTypeRepository;

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
public class EventTypeService extends BaseService<EventType> {

    protected final EventTypeRepository eventtypeRepository;
    
    protected final LiveEventRepository eventsRepository;
    

    public EventTypeService(EventTypeRepository repository, LiveEventRepository eventsRepository)
    {
        super(repository);
        this.eventtypeRepository = repository;
        
        this.eventsRepository = eventsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public EventType update(Long id, EventType eventtypeRequest) {
        EventType existing = eventtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventType not found"));

    // Copier les champs simples
        existing.setName(eventtypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<EventType> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<EventType> search(Map<String, String> filters, Pageable pageable) {
        return super.search(EventType.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<EventType> saveAll(List<EventType> eventtypeList) {
        return super.saveAll(eventtypeList);
    }

}