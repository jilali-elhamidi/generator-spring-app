package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends BaseRepository<Notification, Long> {
}
