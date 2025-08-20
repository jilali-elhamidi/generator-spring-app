package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import org.springframework.stereotype.Repository;

@Repository
public interface PodcastGuestRepository extends BaseRepository<PodcastGuest, Long> {

}
