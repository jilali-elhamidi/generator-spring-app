package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.repository.EventLocationRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class EventLocationService extends BaseService<EventLocation> {

    protected final EventLocationRepository eventlocationRepository;
    private final EmployeeRepository contactPersonRepository;

    public EventLocationService(EventLocationRepository repository,EmployeeRepository contactPersonRepository)
    {
        super(repository);
        this.eventlocationRepository = repository;
        this.contactPersonRepository = contactPersonRepository;
    }

    @Override
    public EventLocation save(EventLocation eventlocation) {

        if (eventlocation.getContactPerson() != null && eventlocation.getContactPerson().getId() != null) {
        Employee contactPerson = contactPersonRepository.findById(eventlocation.getContactPerson().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        eventlocation.setContactPerson(contactPerson);
        }

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

        if (eventlocationRequest.getContactPerson() != null && eventlocationRequest.getContactPerson().getId() != null) {
        Employee contactPerson = contactPersonRepository.findById(eventlocationRequest.getContactPerson().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        existing.setContactPerson(contactPerson);
        }

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