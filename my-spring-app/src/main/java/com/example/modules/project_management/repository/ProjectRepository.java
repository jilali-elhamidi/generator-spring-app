package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends BaseRepository<Project, Long> {
}
