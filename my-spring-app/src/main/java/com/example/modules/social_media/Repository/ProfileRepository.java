package com.example.modules.social_media.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.social_media.model.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends BaseRepository<Profile, Long> {
}
