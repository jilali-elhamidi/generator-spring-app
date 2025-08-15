package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventTypeRepository;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class OnlineEventTypeService extends BaseService<OnlineEventType> {

    protected final OnlineEventTypeRepository onlineeventtypeRepository;
    private final OnlineEventRepository eventsRepository;

    public OnlineEventTypeService(OnlineEventTypeRepository repository,OnlineEventRepository eventsRepository)
    {
        super(repository);
        this.onlineeventtypeRepository = repository;
        this.eventsRepository = eventsRepository;
    }

    @Override
    public OnlineEventType save(OnlineEventType onlineeventtype) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (onlineeventtype.getEvents() != null) {
            List<OnlineEvent> managedEvents = new ArrayList<>();
            for (OnlineEvent item : onlineeventtype.getEvents()) {
            if (item.getId() != null) {
            OnlineEvent existingItem = eventsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setType(onlineeventtype);
            managedEvents.add(existingItem);
            } else {
            item.setType(onlineeventtype);
            managedEvents.add(item);
            }
            }
            onlineeventtype.setEvents(managedEvents);
            }
        
    

    

        return onlineeventtypeRepository.save(onlineeventtype);
    }


    public OnlineEventType update(Long id, OnlineEventType onlineeventtypeRequest) {
        OnlineEventType existing = onlineeventtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OnlineEventType not found"));

    // Copier les champs simples
        existing.setName(onlineeventtypeRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getEvents().clear();

        if (onlineeventtypeRequest.getEvents() != null) {
        for (var item : onlineeventtypeRequest.getEvents()) {
        OnlineEvent existingItem;
        if (item.getId() != null) {
        existingItem = eventsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setType(existing);

        // Ajouter directement dans la collection existante
        existing.getEvents().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return onlineeventtypeRepository.save(existing);
    }


}