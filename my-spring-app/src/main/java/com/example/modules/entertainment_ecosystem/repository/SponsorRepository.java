package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepository extends BaseRepository<Sponsor, Long> {
}
