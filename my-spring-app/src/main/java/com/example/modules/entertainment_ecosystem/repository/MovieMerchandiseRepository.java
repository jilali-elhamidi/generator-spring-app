package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MovieMerchandise;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieMerchandiseRepository extends BaseRepository<MovieMerchandise, Long> {
}
