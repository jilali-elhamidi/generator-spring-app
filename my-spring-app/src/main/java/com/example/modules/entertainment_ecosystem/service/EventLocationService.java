package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.repository.EventLocationRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.repository.LiveEventRepository;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EventLocationService extends BaseService<EventLocation> {

    protected final EventLocationRepository eventlocationRepository;
    private final LiveEventRepository liveEventsRepository;
    private final EmployeeRepository contactPersonRepository;

    public EventLocationService(EventLocationRepository repository, LiveEventRepository liveEventsRepository, EmployeeRepository contactPersonRepository)
    {
        super(repository);
        this.eventlocationRepository = repository;
        this.liveEventsRepository = liveEventsRepository;
        this.contactPersonRepository = contactPersonRepository;
    }

    @Override
    public EventLocation save(EventLocation eventlocation) {
    // ---------- OneToMany ----------
        if (eventlocation.getLiveEvents() != null) {
            List<LiveEvent> managedLiveEvents = new ArrayList<>();
            for (LiveEvent item : eventlocation.getLiveEvents()) {
                if (item.getId() != null) {
                    LiveEvent existingItem = liveEventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));

                     existingItem.setLocation(eventlocation);
                     managedLiveEvents.add(existingItem);
                } else {
                    item.setLocation(eventlocation);
                    managedLiveEvents.add(item);
                }
            }
            eventlocation.setLiveEvents(managedLiveEvents);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (eventlocation.getContactPerson() != null &&
            eventlocation.getContactPerson().getId() != null) {

            Employee existingContactPerson = contactPersonRepository.findById(
                eventlocation.getContactPerson().getId()
            ).orElseThrow(() -> new RuntimeException("Employee not found"));

            eventlocation.setContactPerson(existingContactPerson);
        }
        
    // ---------- OneToOne ----------

    return eventlocationRepository.save(eventlocation);
}


    public EventLocation update(Long id, EventLocation eventlocationRequest) {
        EventLocation existing = eventlocationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EventLocation not found"));

    // Copier les champs simples
        existing.setName(eventlocationRequest.getName());
        existing.setAddress(eventlocationRequest.getAddress());

    // ---------- Relations ManyToOne ----------
        if (eventlocationRequest.getContactPerson() != null &&
            eventlocationRequest.getContactPerson().getId() != null) {

            Employee existingContactPerson = contactPersonRepository.findById(
                eventlocationRequest.getContactPerson().getId()
            ).orElseThrow(() -> new RuntimeException("Employee not found"));

            existing.setContactPerson(existingContactPerson);
        } else {
            existing.setContactPerson(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getLiveEvents().clear();

        if (eventlocationRequest.getLiveEvents() != null) {
            for (var item : eventlocationRequest.getLiveEvents()) {
                LiveEvent existingItem;
                if (item.getId() != null) {
                    existingItem = liveEventsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("LiveEvent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setLocation(existing);
                existing.getLiveEvents().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return eventlocationRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<EventLocation> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        EventLocation entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getLiveEvents() != null) {
            for (var child : entity.getLiveEvents()) {
                
                child.setLocation(null); // retirer la référence inverse
                
            }
            entity.getLiveEvents().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getContactPerson() != null) {
            entity.setContactPerson(null);
        }
        
        repository.delete(entity);
        return true;
    }
}