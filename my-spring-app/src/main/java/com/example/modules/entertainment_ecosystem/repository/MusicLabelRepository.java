package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicLabelRepository extends BaseRepository<MusicLabel, Long> {
}
