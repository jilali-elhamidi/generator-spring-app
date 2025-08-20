package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaylistRepository extends BaseRepository<UserPlaylist, Long> {

}
