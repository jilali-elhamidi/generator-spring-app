package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlaySessionRepository extends BaseRepository<GamePlaySession, Long> {

}
