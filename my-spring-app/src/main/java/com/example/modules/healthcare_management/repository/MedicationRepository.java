package com.example.modules.healthcare_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.healthcare_management.model.Medication;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends BaseRepository<Medication, Long> {

}
