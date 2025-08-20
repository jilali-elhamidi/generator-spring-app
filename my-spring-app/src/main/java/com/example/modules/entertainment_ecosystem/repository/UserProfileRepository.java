package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends BaseRepository<UserProfile, Long> {

}
