package com.example.modules.social_media.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.social_media.model.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends BaseRepository<Notification, Long> {

}
