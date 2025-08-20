package com.example.modules.elearning.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.elearning.model.Lesson;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends BaseRepository<Lesson, Long> {

}
