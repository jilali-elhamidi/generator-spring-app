package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Episode;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends BaseRepository<Episode, Long> {

}
