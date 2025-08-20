package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventTypeRepository;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class OnlineEventTypeService extends BaseService<OnlineEventType> {

    protected final OnlineEventTypeRepository onlineeventtypeRepository;
    private final OnlineEventRepository eventsRepository;

    public OnlineEventTypeService(OnlineEventTypeRepository repository, OnlineEventRepository eventsRepository)
    {
        super(repository);
        this.onlineeventtypeRepository = repository;
        this.eventsRepository = eventsRepository;
    }

    @Override
    public OnlineEventType save(OnlineEventType onlineeventtype) {
    // ---------- OneToMany ----------
        if (onlineeventtype.getEvents() != null) {
            List<OnlineEvent> managedEvents = new ArrayList<>();
            for (OnlineEvent item : onlineeventtype.getEvents()) {
                if (item.getId() != null) {
                    OnlineEvent existingItem = eventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));

                     existingItem.setType(onlineeventtype);
                     managedEvents.add(existingItem);
                } else {
                    item.setType(onlineeventtype);
                    managedEvents.add(item);
                }
            }
            onlineeventtype.setEvents(managedEvents);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return onlineeventtypeRepository.save(onlineeventtype);
}


    public OnlineEventType update(Long id, OnlineEventType onlineeventtypeRequest) {
        OnlineEventType existing = onlineeventtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OnlineEventType not found"));

    // Copier les champs simples
        existing.setName(onlineeventtypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getEvents().clear();

        if (onlineeventtypeRequest.getEvents() != null) {
            for (var item : onlineeventtypeRequest.getEvents()) {
                OnlineEvent existingItem;
                if (item.getId() != null) {
                    existingItem = eventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setType(existing);
                existing.getEvents().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return onlineeventtypeRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<OnlineEventType> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        OnlineEventType entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getEvents() != null) {
            for (var child : entity.getEvents()) {
                
                child.setType(null); // retirer la référence inverse
                
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