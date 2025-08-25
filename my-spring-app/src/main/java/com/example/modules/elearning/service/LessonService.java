package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.repository.LessonRepository;
import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.repository.CourseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class LessonService extends BaseService<Lesson> {

    protected final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonRepository repository, CourseRepository courseRepository)
    {
        super(repository);
        this.lessonRepository = repository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Lesson save(Lesson lesson) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (lesson.getCourse() != null) {
            if (lesson.getCourse().getId() != null) {
                Course existingCourse = courseRepository.findById(
                    lesson.getCourse().getId()
                ).orElseThrow(() -> new RuntimeException("Course not found with id "
                    + lesson.getCourse().getId()));
                lesson.setCourse(existingCourse);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Course newCourse = courseRepository.save(lesson.getCourse());
                lesson.setCourse(newCourse);
            }
        }
        
    // ---------- OneToOne ----------
    return lessonRepository.save(lesson);
}


    public Lesson update(Long id, Lesson lessonRequest) {
        Lesson existing = lessonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lesson not found"));

    // Copier les champs simples
        existing.setTitle(lessonRequest.getTitle());
        existing.setContent(lessonRequest.getContent());
        existing.setCreatedAt(lessonRequest.getCreatedAt());
        existing.setUpdatedAt(lessonRequest.getUpdatedAt());

    // ---------- Relations ManyToOne ----------
        if (lessonRequest.getCourse() != null &&
            lessonRequest.getCourse().getId() != null) {

            Course existingCourse = courseRepository.findById(
                lessonRequest.getCourse().getId()
            ).orElseThrow(() -> new RuntimeException("Course not found"));

            existing.setCourse(existingCourse);
        } else {
            existing.setCourse(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return lessonRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Lesson> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Lesson entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getCourse() != null) {
            entity.setCourse(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Lesson> saveAll(List<Lesson> lessonList) {

        return lessonRepository.saveAll(lessonList);
    }

}