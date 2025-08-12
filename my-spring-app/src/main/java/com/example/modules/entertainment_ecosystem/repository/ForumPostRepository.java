package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumPostRepository extends BaseRepository<ForumPost, Long> {
}
