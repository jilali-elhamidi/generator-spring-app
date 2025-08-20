package com.example.modules.healthcare_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.healthcare_management.model.MedicalFile;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalFileRepository extends BaseRepository<MedicalFile, Long> {

}
