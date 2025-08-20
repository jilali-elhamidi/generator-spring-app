package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import org.springframework.stereotype.Repository;

@Repository
public interface GameReviewCommentRepository extends BaseRepository<GameReviewComment, Long> {

}
