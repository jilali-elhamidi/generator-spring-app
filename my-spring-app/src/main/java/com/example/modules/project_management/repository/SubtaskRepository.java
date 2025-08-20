package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.Subtask;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepository extends BaseRepository<Subtask, Long> {

}
