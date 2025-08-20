package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MovieFestival;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieFestivalRepository extends BaseRepository<MovieFestival, Long> {
}
