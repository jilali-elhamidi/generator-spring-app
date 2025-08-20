package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.UserConnection;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConnectionRepository extends BaseRepository<UserConnection, Long> {
}
