package com.example.modules.elearning.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.elearning.model.Instructor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends BaseRepository<Instructor, Long> {
}
