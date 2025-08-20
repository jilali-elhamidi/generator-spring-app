package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.repository.OnlineEventTypeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class OnlineEventService extends BaseService<OnlineEvent> {

    protected final OnlineEventRepository onlineeventRepository;
    private final UserProfileRepository hostRepository;
    private final UserProfileRepository attendeesRepository;
    private final OnlineEventTypeRepository typeRepository;

    public OnlineEventService(OnlineEventRepository repository,UserProfileRepository hostRepository,UserProfileRepository attendeesRepository,OnlineEventTypeRepository typeRepository)
    {
        super(repository);
        this.onlineeventRepository = repository;
        this.hostRepository = hostRepository;
        this.attendeesRepository = attendeesRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public OnlineEvent save(OnlineEvent onlineevent) {


    

    

    


    

    
        if (onlineevent.getAttendees() != null
        && !onlineevent.getAttendees().isEmpty()) {

        List<UserProfile> attachedAttendees = onlineevent.getAttendees().stream()
        .map(item -> attendeesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId())))
        .toList();

        onlineevent.setAttendees(attachedAttendees);

        // côté propriétaire (UserProfile → OnlineEvent)
        attachedAttendees.forEach(it -> it.getAttendedOnlineEvents().add(onlineevent));
        }
    

    

    if (onlineevent.getHost() != null
        && onlineevent.getHost().getId() != null) {
        UserProfile existingHost = hostRepository.findById(
        onlineevent.getHost().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        onlineevent.setHost(existingHost);
        }
    
    
    if (onlineevent.getType() != null
        && onlineevent.getType().getId() != null) {
        OnlineEventType existingType = typeRepository.findById(
        onlineevent.getType().getId()
        ).orElseThrow(() -> new RuntimeException("OnlineEventType not found"));
        onlineevent.setType(existingType);
        }
    

        return onlineeventRepository.save(onlineevent);
    }


    public OnlineEvent update(Long id, OnlineEvent onlineeventRequest) {
        OnlineEvent existing = onlineeventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));

    // Copier les champs simples
        existing.setName(onlineeventRequest.getName());
        existing.setEventDate(onlineeventRequest.getEventDate());
        existing.setPlatformUrl(onlineeventRequest.getPlatformUrl());
        existing.setDescription(onlineeventRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle
        if (onlineeventRequest.getHost() != null &&
        onlineeventRequest.getHost().getId() != null) {

        UserProfile existingHost = hostRepository.findById(
        onlineeventRequest.getHost().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setHost(existingHost);
        } else {
        existing.setHost(null);
        }
        if (onlineeventRequest.getType() != null &&
        onlineeventRequest.getType().getId() != null) {

        OnlineEventType existingType = typeRepository.findById(
        onlineeventRequest.getType().getId()
        ).orElseThrow(() -> new RuntimeException("OnlineEventType not found"));

        existing.setType(existingType);
        } else {
        existing.setType(null);
        }

// Relations ManyToMany : synchronisation sécurisée
        if (onlineeventRequest.getAttendees() != null) {
        existing.getAttendees().clear();

        List<UserProfile> attendeesList = onlineeventRequest.getAttendees().stream()
        .map(item -> attendeesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found")))
        .collect(Collectors.toList());

        existing.getAttendees().addAll(attendeesList);

        // Mettre à jour le côté inverse
        attendeesList.forEach(it -> {
        if (!it.getAttendedOnlineEvents().contains(existing)) {
        it.getAttendedOnlineEvents().add(existing);
        }
        });
        }

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return onlineeventRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<OnlineEvent> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

OnlineEvent entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    


// --- Dissocier ManyToMany ---

    

    
        if (entity.getAttendees() != null) {
        for (UserProfile item : new ArrayList<>(entity.getAttendees())) {
        
            item.getAttendedOnlineEvents().remove(entity); // retire côté inverse
        
        }
        entity.getAttendees().clear(); // puis vide côté courant
        }
    

    



// --- Dissocier OneToOne ---

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getHost() != null) {
        entity.setHost(null);
        }
    

    

    
        if (entity.getType() != null) {
        entity.setType(null);
        }
    


repository.delete(entity);
return true;
}
}