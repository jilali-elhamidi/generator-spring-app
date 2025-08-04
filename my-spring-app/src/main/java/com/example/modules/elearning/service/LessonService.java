package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.repository.LessonRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonService extends BaseService<Lesson> {

protected final LessonRepository lessonRepository;

public LessonService(LessonRepository repository) {
super(repository);
this.lessonRepository = repository;
}
}
