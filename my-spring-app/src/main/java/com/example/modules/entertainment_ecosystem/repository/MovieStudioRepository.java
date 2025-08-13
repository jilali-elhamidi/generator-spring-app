package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStudioRepository extends BaseRepository<MovieStudio, Long> {
}
