package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.repository.InstructorRepository;
import com.example.modules.elearning.model.Course;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class InstructorService extends BaseService<Instructor> {

    protected final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository repository) {
    super(repository);
    this.instructorRepository = repository;
}

    @Override
    public Instructor save(Instructor instructor) {
        if (instructor.getCourses() != null) {
            for (Course item : instructor.getCourses()) {
                item.setInstructor(instructor);
            }
    }
    return instructorRepository.save(instructor);
    }

    public Instructor update(Long id, Instructor instructorRequest) {
    Instructor existing = instructorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Instructor not found"));

// Copier les champs simples
    existing.setFirstName(instructorRequest.getFirstName());
    existing.setLastName(instructorRequest.getLastName());
    existing.setEmail(instructorRequest.getEmail());

// Relations ManyToOne : mise à jour conditionnelle

// Relations OneToMany : synchronisation sécurisée
    existing.getCourses().clear();
    if (instructorRequest.getCourses() != null) {
        for (var item : instructorRequest.getCourses()) {
        item.setInstructor(existing); // remettre lien inverse
        existing.getCourses().add(item);
        }
    }

    return instructorRepository.save(existing);
    }
}
