package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.repository.LessonRepository;

    
        import com.example.modules.elearning.model.Course;
        import com.example.modules.elearning.repository.CourseRepository;
    
    


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LessonService extends BaseService<Lesson> {

protected final LessonRepository lessonRepository;


    
        private final CourseRepository courseRepository;
    


public LessonService(
LessonRepository repository

    , CourseRepository courseRepository

) {
super(repository);
this.lessonRepository = repository;

    this.courseRepository = courseRepository;

}

@Override
public Lesson save(Lesson lesson) {



    
        if (lesson.getCourse() != null &&
        lesson.getCourse().getId() != null) {
        Course course = courseRepository.findById(lesson.getCourse().getId())
        .orElseThrow(() -> new RuntimeException("Course not found"));
        lesson.setCourse(course);
        }
    




    


return lessonRepository.save(lesson);
}
}
