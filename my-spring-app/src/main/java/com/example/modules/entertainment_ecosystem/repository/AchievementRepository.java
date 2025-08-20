package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Achievement;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends BaseRepository<Achievement, Long> {
}
