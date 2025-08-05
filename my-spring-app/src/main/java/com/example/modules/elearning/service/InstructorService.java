package com.example.modules.elearning.service;

import com.example.core.service.BaseService;
import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.repository.InstructorRepository;

    
    
        import com.example.modules.elearning.model.Course;
    


import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class InstructorService extends BaseService<Instructor> {

protected final InstructorRepository instructorRepository;


    


public InstructorService(
InstructorRepository repository

    

) {
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
}
