package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventTypeRepository;

import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OnlineEventTypeService extends BaseService<OnlineEventType> {

    protected final OnlineEventTypeRepository onlineeventtypeRepository;
    
    protected final OnlineEventRepository eventsRepository;
    

    public OnlineEventTypeService(OnlineEventTypeRepository repository, OnlineEventRepository eventsRepository)
    {
        super(repository);
        this.onlineeventtypeRepository = repository;
        
        this.eventsRepository = eventsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public OnlineEventType update(Long id, OnlineEventType onlineeventtypeRequest) {
        OnlineEventType existing = onlineeventtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OnlineEventType not found"));

    // Copier les champs simples
        existing.setName(onlineeventtypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<OnlineEventType> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<OnlineEventType> search(Map<String, String> filters, Pageable pageable) {
        return super.search(OnlineEventType.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<OnlineEventType> saveAll(List<OnlineEventType> onlineeventtypeList) {
        return super.saveAll(onlineeventtypeList);
    }

}