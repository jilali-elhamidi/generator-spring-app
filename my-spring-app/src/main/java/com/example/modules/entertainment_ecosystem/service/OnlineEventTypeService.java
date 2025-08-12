package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventTypeRepository;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class OnlineEventTypeService extends BaseService<OnlineEventType> {

    protected final OnlineEventTypeRepository onlineeventtypeRepository;

    public OnlineEventTypeService(OnlineEventTypeRepository repository)
    {
        super(repository);
        this.onlineeventtypeRepository = repository;
    }

    @Override
    public OnlineEventType save(OnlineEventType onlineeventtype) {

        if (onlineeventtype.getEvents() != null) {
            for (OnlineEvent item : onlineeventtype.getEvents()) {
            item.setType(onlineeventtype);
            }
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

        existing.getEvents().clear();
        if (onlineeventtypeRequest.getEvents() != null) {
            for (var item : onlineeventtypeRequest.getEvents()) {
            item.setType(existing);
            existing.getEvents().add(item);
            }
        }

        return onlineeventtypeRepository.save(existing);
    }
}