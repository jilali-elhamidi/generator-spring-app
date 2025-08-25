package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.repository.InstructorRepository;
import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.repository.CourseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class InstructorService extends BaseService<Instructor> {

    protected final InstructorRepository instructorRepository;
    private final CourseRepository coursesRepository;

    public InstructorService(InstructorRepository repository, CourseRepository coursesRepository)
    {
        super(repository);
        this.instructorRepository = repository;
        this.coursesRepository = coursesRepository;
    }

    @Override
    public Instructor save(Instructor instructor) {
    // ---------- OneToMany ----------
        if (instructor.getCourses() != null) {
            List<Course> managedCourses = new ArrayList<>();
            for (Course item : instructor.getCourses()) {
                if (item.getId() != null) {
                    Course existingItem = coursesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Course not found"));

                     existingItem.setInstructor(instructor);
                     managedCourses.add(existingItem);
                } else {
                    item.setInstructor(instructor);
                    managedCourses.add(item);
                }
            }
            instructor.setCourses(managedCourses);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return instructorRepository.save(instructor);
}


    public Instructor update(Long id, Instructor instructorRequest) {
        Instructor existing = instructorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Instructor not found"));

    // Copier les champs simples
        existing.setFirstName(instructorRequest.getFirstName());
        existing.setLastName(instructorRequest.getLastName());
        existing.setEmail(instructorRequest.getEmail());
        existing.setCreatedAt(instructorRequest.getCreatedAt());
        existing.setUpdatedAt(instructorRequest.getUpdatedAt());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getCourses().clear();

        if (instructorRequest.getCourses() != null) {
            for (var item : instructorRequest.getCourses()) {
                Course existingItem;
                if (item.getId() != null) {
                    existingItem = coursesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Course not found"));
                } else {
                existingItem = item;
                }

                existingItem.setInstructor(existing);
                existing.getCourses().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return instructorRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Instructor> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Instructor entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getCourses() != null) {
            for (var child : entity.getCourses()) {
                // retirer la référence inverse
                child.setInstructor(null);
            }
            entity.getCourses().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Instructor> saveAll(List<Instructor> instructorList) {

        return instructorRepository.saveAll(instructorList);
    }

}