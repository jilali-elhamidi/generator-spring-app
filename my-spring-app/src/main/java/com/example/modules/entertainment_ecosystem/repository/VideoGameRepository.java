package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoGameRepository extends BaseRepository<VideoGame, Long> {
}
