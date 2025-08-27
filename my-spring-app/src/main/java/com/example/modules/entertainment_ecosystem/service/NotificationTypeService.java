package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.NotificationType;
import com.example.modules.entertainment_ecosystem.repository.NotificationTypeRepository;

import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.repository.NotificationRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class NotificationTypeService extends BaseService<NotificationType> {

    protected final NotificationTypeRepository notificationtypeRepository;
    
    protected final NotificationRepository notificationsRepository;
    

    public NotificationTypeService(NotificationTypeRepository repository, NotificationRepository notificationsRepository)
    {
        super(repository);
        this.notificationtypeRepository = repository;
        
        this.notificationsRepository = notificationsRepository;
        
    }

    @Transactional
    @Override
    public NotificationType save(NotificationType notificationtype) {
    // ---------- OneToMany ----------
        if (notificationtype.getNotifications() != null) {
            List<Notification> managedNotifications = new ArrayList<>();
            for (Notification item : notificationtype.getNotifications()) {
                if (item.getId() != null) {
                    Notification existingItem = notificationsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Notification not found"));

                     existingItem.setType(notificationtype);
                     managedNotifications.add(existingItem);
                } else {
                    item.setType(notificationtype);
                    managedNotifications.add(item);
                }
            }
            notificationtype.setNotifications(managedNotifications);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return notificationtypeRepository.save(notificationtype);
}

    @Transactional
    @Override
    public NotificationType update(Long id, NotificationType notificationtypeRequest) {
        NotificationType existing = notificationtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("NotificationType not found"));

    // Copier les champs simples
        existing.setName(notificationtypeRequest.getName());
        existing.setTemplate(notificationtypeRequest.getTemplate());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getNotifications().clear();

        if (notificationtypeRequest.getNotifications() != null) {
            for (var item : notificationtypeRequest.getNotifications()) {
                Notification existingItem;
                if (item.getId() != null) {
                    existingItem = notificationsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Notification not found"));
                } else {
                existingItem = item;
                }

                existingItem.setType(existing);
                existing.getNotifications().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return notificationtypeRepository.save(existing);
}

    // Pagination simple
    public Page<NotificationType> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<NotificationType> search(Map<String, String> filters, Pageable pageable) {
        return super.search(NotificationType.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<NotificationType> saveAll(List<NotificationType> notificationtypeList) {
        return super.saveAll(notificationtypeList);
    }

}