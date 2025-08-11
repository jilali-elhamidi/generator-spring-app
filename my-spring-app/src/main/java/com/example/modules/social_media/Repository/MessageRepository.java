package com.example.modules.social_media.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.social_media.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends BaseRepository<Message, Long> {
}
