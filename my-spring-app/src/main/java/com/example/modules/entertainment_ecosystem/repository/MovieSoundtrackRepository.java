package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MovieSoundtrack;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSoundtrackRepository extends BaseRepository<MovieSoundtrack, Long> {
}
