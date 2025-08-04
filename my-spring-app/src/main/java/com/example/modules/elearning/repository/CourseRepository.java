package com.example.modules.elearning.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.elearning.model.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends BaseRepository<Course, Long> {
}
