package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.Milestone;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneRepository extends BaseRepository<Milestone, Long> {

}
