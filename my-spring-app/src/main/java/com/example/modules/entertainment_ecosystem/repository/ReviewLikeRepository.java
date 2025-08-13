package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends BaseRepository<ReviewLike, Long> {
}
