package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicTrackRepository extends BaseRepository<MusicTrack, Long> {
}
