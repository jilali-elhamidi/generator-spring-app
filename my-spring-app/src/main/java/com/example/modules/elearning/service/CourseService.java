package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.repository.CourseRepository;

    
    
        import com.example.modules.elearning.model.Lesson;
    

    
        import com.example.modules.elearning.model.Instructor;
        import com.example.modules.elearning.repository.InstructorRepository;
    
    


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CourseService extends BaseService<Course> {

protected final CourseRepository courseRepository;


    

    
        private final InstructorRepository instructorRepository;
    


public CourseService(
CourseRepository repository

    

    , InstructorRepository instructorRepository

) {
super(repository);
this.courseRepository = repository;

    

    this.instructorRepository = instructorRepository;

}

@Override
public Course save(Course course) {



    

    
        if (course.getInstructor() != null &&
        course.getInstructor().getId() != null) {
        Instructor instructor = instructorRepository.findById(course.getInstructor().getId())
        .orElseThrow(() -> new RuntimeException("Instructor not found"));
        course.setInstructor(instructor);
        }
    




    
        if (course.getLessons() != null) {
        for (Lesson item : course.getLessons()) {
        item.setCourse(course);
        }
        }
    

    


return courseRepository.save(course);
}
}
