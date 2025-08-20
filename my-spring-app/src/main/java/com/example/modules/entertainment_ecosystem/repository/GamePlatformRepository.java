package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlatformRepository extends BaseRepository<GamePlatform, Long> {

}
