package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Country;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends BaseRepository<Country, Long> {
}
