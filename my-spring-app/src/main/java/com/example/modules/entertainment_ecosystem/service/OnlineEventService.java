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
        }

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return onlineeventRepository.save(existing);
    }


}