package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaylistItemRepository extends BaseRepository<UserPlaylistItem, Long> {
}
