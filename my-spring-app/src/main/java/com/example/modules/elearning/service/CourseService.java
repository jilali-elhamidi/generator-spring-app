package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService extends BaseService<Course> {

protected final CourseRepository courseRepository;

public CourseService(CourseRepository repository) {
super(repository);
this.courseRepository = repository;
}
}
