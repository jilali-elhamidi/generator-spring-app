package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.NotificationType;
import com.example.modules.entertainment_ecosystem.repository.NotificationTypeRepository;
import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.repository.NotificationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class NotificationTypeService extends BaseService<NotificationType> {

    protected final NotificationTypeRepository notificationtypeRepository;
    private final NotificationRepository notificationsRepository;

    public NotificationTypeService(NotificationTypeRepository repository,NotificationRepository notificationsRepository)
    {
        super(repository);
        this.notificationtypeRepository = repository;
        this.notificationsRepository = notificationsRepository;
    }

    @Override
    public NotificationType save(NotificationType notificationtype) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (notificationtype.getNotifications() != null) {
            List<Notification> managedNotifications = new ArrayList<>();
            for (Notification item : notificationtype.getNotifications()) {
            if (item.getId() != null) {
            Notification existingItem = notificationsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Notification not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setType(notificationtype);
            managedNotifications.add(existingItem);
            } else {
            item.setType(notificationtype);
            managedNotifications.add(item);
            }
            }
            notificationtype.setNotifications(managedNotifications);
            }
        
    


    

    

        return notificationtypeRepository.save(notificationtype);
    }


    public NotificationType update(Long id, NotificationType notificationtypeRequest) {
        NotificationType existing = notificationtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("NotificationType not found"));

    // Copier les champs simples
        existing.setName(notificationtypeRequest.getName());
        existing.setTemplate(notificationtypeRequest.getTemplate());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getNotifications().clear();

        if (notificationtypeRequest.getNotifications() != null) {
        for (var item : notificationtypeRequest.getNotifications()) {
        Notification existingItem;
        if (item.getId() != null) {
        existingItem = notificationsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Notification not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setType(existing);

        // Ajouter directement dans la collection existante
        existing.getNotifications().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return notificationtypeRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<NotificationType> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

NotificationType entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getNotifications() != null) {
        for (var child : entity.getNotifications()) {
        
            child.setType(null); // retirer la référence inverse
        
        }
        entity.getNotifications().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}