package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.TimeLog;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLogRepository extends BaseRepository<TimeLog, Long> {

}
