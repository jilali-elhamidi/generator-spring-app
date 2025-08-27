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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OnlineEventService extends BaseService<OnlineEvent> {

    protected final OnlineEventRepository onlineeventRepository;
    
    protected final UserProfileRepository hostRepository;
    
    protected final UserProfileRepository attendeesRepository;
    
    protected final OnlineEventTypeRepository typeRepository;
    

    public OnlineEventService(OnlineEventRepository repository, UserProfileRepository hostRepository, UserProfileRepository attendeesRepository, OnlineEventTypeRepository typeRepository)
    {
        super(repository);
        this.onlineeventRepository = repository;
        
        this.hostRepository = hostRepository;
        
        this.attendeesRepository = attendeesRepository;
        
        this.typeRepository = typeRepository;
        
    }

    @Transactional
    @Override
    public OnlineEvent save(OnlineEvent onlineevent) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (onlineevent.getAttendees() != null &&
            !onlineevent.getAttendees().isEmpty()) {

            List<UserProfile> attachedAttendees = new ArrayList<>();
            for (UserProfile item : onlineevent.getAttendees()) {
                if (item.getId() != null) {
                    UserProfile existingItem = attendeesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedAttendees.add(existingItem);
                } else {

                    UserProfile newItem = attendeesRepository.save(item);
                    attachedAttendees.add(newItem);
                }
            }

            onlineevent.setAttendees(attachedAttendees);

            // côté propriétaire (UserProfile → OnlineEvent)
            attachedAttendees.forEach(it -> it.getAttendedOnlineEvents().add(onlineevent));
        }
        
    // ---------- ManyToOne ----------
        if (onlineevent.getHost() != null) {
            if (onlineevent.getHost().getId() != null) {
                UserProfile existingHost = hostRepository.findById(
                    onlineevent.getHost().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + onlineevent.getHost().getId()));
                onlineevent.setHost(existingHost);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newHost = hostRepository.save(onlineevent.getHost());
                onlineevent.setHost(newHost);
            }
        }
        
        if (onlineevent.getType() != null) {
            if (onlineevent.getType().getId() != null) {
                OnlineEventType existingType = typeRepository.findById(
                    onlineevent.getType().getId()
                ).orElseThrow(() -> new RuntimeException("OnlineEventType not found with id "
                    + onlineevent.getType().getId()));
                onlineevent.setType(existingType);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                OnlineEventType newType = typeRepository.save(onlineevent.getType());
                onlineevent.setType(newType);
            }
        }
        
    // ---------- OneToOne ----------
    return onlineeventRepository.save(onlineevent);
}

    @Transactional
    @Override
    public OnlineEvent update(Long id, OnlineEvent onlineeventRequest) {
        OnlineEvent existing = onlineeventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OnlineEvent not found"));

    // Copier les champs simples
        existing.setName(onlineeventRequest.getName());
        existing.setEventDate(onlineeventRequest.getEventDate());
        existing.setPlatformUrl(onlineeventRequest.getPlatformUrl());
        existing.setDescription(onlineeventRequest.getDescription());

    // ---------- Relations ManyToOne ----------
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
        
    // ---------- Relations ManyToMany ----------
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
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return onlineeventRepository.save(existing);
}

    // Pagination simple
    public Page<OnlineEvent> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<OnlineEvent> search(Map<String, String> filters, Pageable pageable) {
        return super.search(OnlineEvent.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<OnlineEvent> saveAll(List<OnlineEvent> onlineeventList) {
        return super.saveAll(onlineeventList);
    }

}