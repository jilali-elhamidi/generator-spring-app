package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.repository.CourseRepository;
import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.repository.InstructorRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class CourseService extends BaseService<Course> {

    protected final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository repository, InstructorRepository instructorRepository) {
    super(repository);
    this.courseRepository = repository;
    this.instructorRepository = instructorRepository;
}

    @Override
    public Course save(Course course) {
        if (course.getInstructor() != null && course.getInstructor().getId() != null) {
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

    public Course update(Long id, Course courseRequest) {
    Course existing = courseRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Course not found"));

// Copier les champs simples
    existing.setTitle(courseRequest.getTitle());
    existing.setDescription(courseRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle
    if (courseRequest.getInstructor() != null &&courseRequest.getInstructor().getId() != null) {
    Instructor instructor = instructorRepository.findById(courseRequest.getInstructor().getId())
        .orElseThrow(() -> new RuntimeException("Instructor not found"));
    existing.setInstructor(instructor);
    }
    // Sinon on garde la relation existante

// Relations OneToMany : synchronisation sécurisée
    existing.getLessons().clear();
    if (courseRequest.getLessons() != null) {
        for (var item : courseRequest.getLessons()) {
        item.setCourse(existing); // remettre lien inverse
        existing.getLessons().add(item);
        }
    }

    return courseRepository.save(existing);
    }
}
