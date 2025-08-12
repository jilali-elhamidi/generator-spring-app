package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends BaseRepository<Artist, Long> {
}
