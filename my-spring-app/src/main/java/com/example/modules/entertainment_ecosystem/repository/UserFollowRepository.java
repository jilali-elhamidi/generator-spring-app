package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.UserFollow;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowRepository extends BaseRepository<UserFollow, Long> {
}
