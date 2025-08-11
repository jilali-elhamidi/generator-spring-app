package com.example.modules.social_media.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.social_media.model.Tag;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<Tag, Long> {
}
