package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.UserActivityLog;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityLogRepository extends BaseRepository<UserActivityLog, Long> {

}
