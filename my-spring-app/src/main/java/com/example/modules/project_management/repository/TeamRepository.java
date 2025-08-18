package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.Team;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends BaseRepository<Team, Long> {
}
