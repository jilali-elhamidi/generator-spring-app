package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface PodcastCategoryRepository extends BaseRepository<PodcastCategory, Long> {

}
