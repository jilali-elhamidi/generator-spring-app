package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.repository.NotificationRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.NotificationType;
import com.example.modules.entertainment_ecosystem.repository.NotificationTypeRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class NotificationService extends BaseService<Notification> {

    protected final NotificationRepository notificationRepository;
    
    protected final UserProfileRepository recipientRepository;
    
    protected final NotificationTypeRepository typeRepository;
    

    public NotificationService(NotificationRepository repository, UserProfileRepository recipientRepository, NotificationTypeRepository typeRepository)
    {
        super(repository);
        this.notificationRepository = repository;
        
        this.recipientRepository = recipientRepository;
        
        this.typeRepository = typeRepository;
        
    }

    @Transactional
    @Override
    public Notification save(Notification notification) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (notification.getRecipient() != null) {
            if (notification.getRecipient().getId() != null) {
                UserProfile existingRecipient = recipientRepository.findById(
                    notification.getRecipient().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + notification.getRecipient().getId()));
                notification.setRecipient(existingRecipient);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newRecipient = recipientRepository.save(notification.getRecipient());
                notification.setRecipient(newRecipient);
            }
        }
        
        if (notification.getType() != null) {
            if (notification.getType().getId() != null) {
                NotificationType existingType = typeRepository.findById(
                    notification.getType().getId()
                ).orElseThrow(() -> new RuntimeException("NotificationType not found with id "
                    + notification.getType().getId()));
                notification.setType(existingType);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                NotificationType newType = typeRepository.save(notification.getType());
                notification.setType(newType);
            }
        }
        
    // ---------- OneToOne ----------
    return notificationRepository.save(notification);
}

    @Transactional
    @Override
    public Notification update(Long id, Notification notificationRequest) {
        Notification existing = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found"));

    // Copier les champs simples
        existing.setTitle(notificationRequest.getTitle());
        existing.setMessage(notificationRequest.getMessage());
        existing.setSentDate(notificationRequest.getSentDate());
        existing.setIsRead(notificationRequest.getIsRead());

    // ---------- Relations ManyToOne ----------
        if (notificationRequest.getRecipient() != null &&
            notificationRequest.getRecipient().getId() != null) {

            UserProfile existingRecipient = recipientRepository.findById(
                notificationRequest.getRecipient().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setRecipient(existingRecipient);
        } else {
            existing.setRecipient(null);
        }
        
        if (notificationRequest.getType() != null &&
            notificationRequest.getType().getId() != null) {

            NotificationType existingType = typeRepository.findById(
                notificationRequest.getType().getId()
            ).orElseThrow(() -> new RuntimeException("NotificationType not found"));

            existing.setType(existingType);
        } else {
            existing.setType(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return notificationRepository.save(existing);
}

    // Pagination simple
    public Page<Notification> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Notification> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Notification.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Notification> saveAll(List<Notification> notificationList) {
        return super.saveAll(notificationList);
    }

}