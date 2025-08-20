package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import org.springframework.stereotype.Repository;

@Repository
public interface GameReviewDownvoteRepository extends BaseRepository<GameReviewDownvote, Long> {

}
