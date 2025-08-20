package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.EventLocation;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLocationRepository extends BaseRepository<EventLocation, Long> {

}
