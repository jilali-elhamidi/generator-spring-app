package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveEventRepository extends BaseRepository<LiveEvent, Long> {
}
