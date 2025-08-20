package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.repository.CourseRepository;
import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.repository.LessonRepository;
import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.repository.InstructorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class CourseService extends BaseService<Course> {

    protected final CourseRepository courseRepository;
    private final LessonRepository lessonsRepository;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository repository, LessonRepository lessonsRepository, InstructorRepository instructorRepository)
    {
        super(repository);
        this.courseRepository = repository;
        this.lessonsRepository = lessonsRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Course save(Course course) {
    // ---------- OneToMany ----------
        if (course.getLessons() != null) {
            List<Lesson> managedLessons = new ArrayList<>();
            for (Lesson item : course.getLessons()) {
                if (item.getId() != null) {
                    Lesson existingItem = lessonsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Lesson not found"));

                     existingItem.setCourse(course);
                     managedLessons.add(existingItem);
                } else {
                    item.setCourse(course);
                    managedLessons.add(item);
                }
            }
            course.setLessons(managedLessons);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (course.getInstructor() != null &&
            course.getInstructor().getId() != null) {

            Instructor existingInstructor = instructorRepository.findById(
                course.getInstructor().getId()
            ).orElseThrow(() -> new RuntimeException("Instructor not found"));

            course.setInstructor(existingInstructor);
        }
        
    // ---------- OneToOne ----------

    return courseRepository.save(course);
}


    public Course update(Long id, Course courseRequest) {
        Course existing = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found"));

    // Copier les champs simples
        existing.setTitle(courseRequest.getTitle());
        existing.setDescription(courseRequest.getDescription());
        existing.setCreatedAt(courseRequest.getCreatedAt());
        existing.setUpdatedAt(courseRequest.getUpdatedAt());

    // ---------- Relations ManyToOne ----------
        if (courseRequest.getInstructor() != null &&
            courseRequest.getInstructor().getId() != null) {

            Instructor existingInstructor = instructorRepository.findById(
                courseRequest.getInstructor().getId()
            ).orElseThrow(() -> new RuntimeException("Instructor not found"));

            existing.setInstructor(existingInstructor);
        } else {
            existing.setInstructor(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getLessons().clear();

        if (courseRequest.getLessons() != null) {
            for (var item : courseRequest.getLessons()) {
                Lesson existingItem;
                if (item.getId() != null) {
                    existingItem = lessonsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Lesson not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCourse(existing);
                existing.getLessons().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------

    return courseRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Course> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Course entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getLessons() != null) {
            for (var child : entity.getLessons()) {
                
                child.setCourse(null); // retirer la référence inverse
                
            }
            entity.getLessons().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getInstructor() != null) {
            entity.setInstructor(null);
        }
        
        repository.delete(entity);
        return true;
    }
}