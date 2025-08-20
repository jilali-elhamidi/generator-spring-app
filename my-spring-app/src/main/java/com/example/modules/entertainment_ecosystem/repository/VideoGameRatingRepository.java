package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoGameRatingRepository extends BaseRepository<VideoGameRating, Long> {

}
