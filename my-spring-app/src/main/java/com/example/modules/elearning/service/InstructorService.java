package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.repository.InstructorRepository;
import org.springframework.stereotype.Service;

@Service
public class InstructorService extends BaseService<Instructor> {

protected final InstructorRepository instructorRepository;

public InstructorService(InstructorRepository repository) {
super(repository);
this.instructorRepository = repository;
}
}
