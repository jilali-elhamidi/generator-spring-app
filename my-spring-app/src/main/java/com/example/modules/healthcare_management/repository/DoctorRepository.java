package com.example.modules.healthcare_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.healthcare_management.model.Doctor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor, Long> {

}
