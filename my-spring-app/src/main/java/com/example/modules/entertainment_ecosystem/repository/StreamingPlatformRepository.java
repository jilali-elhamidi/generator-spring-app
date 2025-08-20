package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamingPlatformRepository extends BaseRepository<StreamingPlatform, Long> {
}
