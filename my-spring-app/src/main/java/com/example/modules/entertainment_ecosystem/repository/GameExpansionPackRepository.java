package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import org.springframework.stereotype.Repository;

@Repository
public interface GameExpansionPackRepository extends BaseRepository<GameExpansionPack, Long> {
}
