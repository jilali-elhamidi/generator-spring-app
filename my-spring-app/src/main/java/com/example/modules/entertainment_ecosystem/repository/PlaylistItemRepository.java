package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.PlaylistItem;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistItemRepository extends BaseRepository<PlaylistItem, Long> {
}
