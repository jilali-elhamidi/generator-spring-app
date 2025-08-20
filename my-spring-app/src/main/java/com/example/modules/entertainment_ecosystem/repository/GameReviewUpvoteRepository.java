package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import org.springframework.stereotype.Repository;

@Repository
public interface GameReviewUpvoteRepository extends BaseRepository<GameReviewUpvote, Long> {
}
