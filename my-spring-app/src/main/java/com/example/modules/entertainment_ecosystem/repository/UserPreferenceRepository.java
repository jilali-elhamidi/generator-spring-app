package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.UserPreference;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepository extends BaseRepository<UserPreference, Long> {

}
