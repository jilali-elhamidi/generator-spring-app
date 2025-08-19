package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.repository.NotificationRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class NotificationService extends BaseService<Notification> {

    protected final NotificationRepository notificationRepository;
    private final UserProfileRepository recipientRepository;

    public NotificationService(NotificationRepository repository,UserProfileRepository recipientRepository)
    {
        super(repository);
        this.notificationRepository = repository;
        this.recipientRepository = recipientRepository;
    }

    @Override
    public Notification save(Notification notification) {


    


    

    if (notification.getRecipient() != null
        && notification.getRecipient().getId() != null) {
        UserProfile existingRecipient = recipientRepository.findById(
        notification.getRecipient().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        notification.setRecipient(existingRecipient);
        }
    

        return notificationRepository.save(notification);
    }


    public Notification update(Long id, Notification notificationRequest) {
        Notification existing = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found"));

    // Copier les champs simples
        existing.setTitle(notificationRequest.getTitle());
        existing.setMessage(notificationRequest.getMessage());
        existing.setSentDate(notificationRequest.getSentDate());
        existing.setIsRead(notificationRequest.getIsRead());

// Relations ManyToOne : mise à jour conditionnelle
        if (notificationRequest.getRecipient() != null &&
        notificationRequest.getRecipient().getId() != null) {

        UserProfile existingRecipient = recipientRepository.findById(
        notificationRequest.getRecipient().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setRecipient(existingRecipient);
        } else {
        existing.setRecipient(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


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