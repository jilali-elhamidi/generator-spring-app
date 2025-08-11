package com.example.modules.social_media.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.social_media.model.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends BaseRepository<Post, Long> {
}
