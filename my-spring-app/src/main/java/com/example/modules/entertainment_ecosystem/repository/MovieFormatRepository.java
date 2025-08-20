package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieFormatRepository extends BaseRepository<MovieFormat, Long> {

}
