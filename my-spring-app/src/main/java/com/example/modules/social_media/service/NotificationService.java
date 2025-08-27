package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Notification;
import com.example.modules.social_media.repository.NotificationRepository;

import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;


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
    
    protected final ProfileRepository recipientRepository;
    

    public NotificationService(NotificationRepository repository, ProfileRepository recipientRepository)
    {
        super(repository);
        this.notificationRepository = repository;
        
        this.recipientRepository = recipientRepository;
        
    }

    @Transactional
    @Override
    public Notification save(Notification notification) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (notification.getRecipient() != null) {
            if (notification.getRecipient().getId() != null) {
                Profile existingRecipient = recipientRepository.findById(
                    notification.getRecipient().getId()
                ).orElseThrow(() -> new RuntimeException("Profile not found with id "
                    + notification.getRecipient().getId()));
                notification.setRecipient(existingRecipient);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Profile newRecipient = recipientRepository.save(notification.getRecipient());
                notification.setRecipient(newRecipient);
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
        existing.setType(notificationRequest.getType());
        existing.setContent(notificationRequest.getContent());
        existing.setCreationDate(notificationRequest.getCreationDate());
        existing.setIsRead(notificationRequest.getIsRead());

    // ---------- Relations ManyToOne ----------
        if (notificationRequest.getRecipient() != null &&
            notificationRequest.getRecipient().getId() != null) {

            Profile existingRecipient = recipientRepository.findById(
                notificationRequest.getRecipient().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            existing.setRecipient(existingRecipient);
        } else {
            existing.setRecipient(null);
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