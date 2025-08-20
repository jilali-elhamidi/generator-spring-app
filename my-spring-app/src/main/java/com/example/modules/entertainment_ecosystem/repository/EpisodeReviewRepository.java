package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.EpisodeReview;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeReviewRepository extends BaseRepository<EpisodeReview, Long> {
}
