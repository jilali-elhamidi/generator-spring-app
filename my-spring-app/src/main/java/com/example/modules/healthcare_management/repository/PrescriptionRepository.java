package com.example.modules.healthcare_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.healthcare_management.model.Prescription;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends BaseRepository<Prescription, Long> {
}
