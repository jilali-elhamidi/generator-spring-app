package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Notification;
import com.example.modules.social_media.repository.NotificationRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class NotificationService extends BaseService<Notification> {

    protected final NotificationRepository notificationRepository;
    private final ProfileRepository recipientRepository;

    public NotificationService(NotificationRepository repository,ProfileRepository recipientRepository)
    {
        super(repository);
        this.notificationRepository = repository;
        this.recipientRepository = recipientRepository;
    }

    @Override
    public Notification save(Notification notification) {

        if (notification.getRecipient() != null && notification.getRecipient().getId() != null) {
        Profile recipient = recipientRepository.findById(notification.getRecipient().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        notification.setRecipient(recipient);
        }

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

// Relations ManyToOne : mise à jour conditionnelle

        if (notificationRequest.getRecipient() != null && notificationRequest.getRecipient().getId() != null) {
        Profile recipient = recipientRepository.findById(notificationRequest.getRecipient().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        existing.setRecipient(recipient);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return notificationRepository.save(existing);
    }
}