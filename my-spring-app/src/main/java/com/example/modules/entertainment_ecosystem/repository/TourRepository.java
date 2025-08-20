package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Tour;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends BaseRepository<Tour, Long> {

}
