package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends BaseRepository<Genre, Long> {
}
