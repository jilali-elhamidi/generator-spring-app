package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumCategoryRepository extends BaseRepository<ForumCategory, Long> {
}
