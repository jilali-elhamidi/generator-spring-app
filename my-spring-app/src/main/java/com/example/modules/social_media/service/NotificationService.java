package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Notification;
import com.example.modules.social_media.repository.NotificationRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class NotificationService extends BaseService<Notification> {

    protected final NotificationRepository notificationRepository;
    private final ProfileRepository recipientRepository;

    public NotificationService(NotificationRepository repository, ProfileRepository recipientRepository)
    {
        super(repository);
        this.notificationRepository = repository;
        this.recipientRepository = recipientRepository;
    }

    @Override
    public Notification save(Notification notification) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (notification.getRecipient() != null &&
            notification.getRecipient().getId() != null) {

            Profile existingRecipient = recipientRepository.findById(
                notification.getRecipient().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            notification.setRecipient(existingRecipient);
        }
        
    // ---------- OneToOne ----------
    return notificationRepository.save(notification);
}


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
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return notificationRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Notification> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Notification entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getRecipient() != null) {
            entity.setRecipient(null);
        }
        
        repository.delete(entity);
        return true;
    }
}