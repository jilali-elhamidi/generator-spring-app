package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MusicGenreCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicGenreCategoryRepository extends BaseRepository<MusicGenreCategory, Long> {
}
