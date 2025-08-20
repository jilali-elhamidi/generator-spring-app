package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Album;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends BaseRepository<Album, Long> {
}
